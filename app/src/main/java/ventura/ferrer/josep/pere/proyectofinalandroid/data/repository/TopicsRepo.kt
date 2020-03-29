package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import android.content.Context
import android.os.Handler
import retrofit2.Response
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.TopicsService
import ventura.ferrer.josep.pere.proyectofinalandroid.database.EhHoDatabase
import ventura.ferrer.josep.pere.proyectofinalandroid.database.TopicsNewEntity
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModelResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ListTopic
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import kotlin.concurrent.thread


object TopicsRepo: TopicsRepository {

    lateinit var db: EhHoDatabase
    lateinit var ctx: Context
    lateinit var retroF: Retrofit

    override suspend fun getTopics(): Response<ListTopic> {
        val a = retroF.create(TopicsService::class.java).getTopics()
        return a
    }

    override suspend fun createTopic(createTopicModel: CreateTopicModel): Response<CreateTopicModelResponse> {
        val a = retroF.create(TopicsService::class.java).loginUserWithCoroutines(createTopicModel)

        return a
    }

    override suspend fun loadMoreTopics(no_definitions: Boolean, page: Int): Response<ListTopic> {
        val a = retroF.create(TopicsService::class.java)
            .loadMoreTopicsWithCoroutines(no_definitions, page)

        return a
    }

    fun insertTopicsToDB(listTopics: List<Topic>) {
        thread {
            db.topicsNewDao().insertAll(listTopics.toEntity())
        }

    }

    fun getTopicsFromDB(onSuccess: (List<Topic>) -> Unit, onError:(String)->Unit){
        val handler = Handler(ctx.mainLooper)
        thread {
            val topicList = db.topicsNewDao().getTopics().toModel()
            val runnable = Runnable {
                if (topicList.isNotEmpty()) {
                    onSuccess(topicList)
                }else{
                    onError("No internet Conection and no data to load from local DB")
                }
            }
            handler.post(runnable)
        }
    }
}

private fun List<TopicsNewEntity>.toModel(): List<Topic> = map { it.toModel() }

private fun TopicsNewEntity.toModel(): Topic = Topic(
    id = topicId,
    title = title,
    fecha = date,
    posts = posts,
    views = views,
    avatar = null
)

private fun List<Topic>.toEntity(): List<TopicsNewEntity> = map { it.toEntity() }

private fun Topic.toEntity(): TopicsNewEntity = TopicsNewEntity(
    topicId = id,
    title = title,
    date = fecha,
    posts = posts,
    views = views
)

