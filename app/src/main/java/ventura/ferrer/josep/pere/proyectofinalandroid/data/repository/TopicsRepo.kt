package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import android.content.Context
import retrofit2.Response
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.TopicsService
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModelResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ListTopic


object TopicsRepo: TopicsRepository {

    lateinit var ctx: Context
    lateinit var retroF: Retrofit

    override suspend fun getTopics(): Response<ListTopic> {
        val a =    retroF.create(TopicsService::class.java).getTopics()
        println("" + retroF.toString())
        return a
    }

    override suspend fun createTopic(createTopicModel: CreateTopicModel): Response<CreateTopicModelResponse> {
        val a =    retroF.create(TopicsService::class.java).loginUserWithCoroutines(createTopicModel)
        println("" + retroF.toString())
        return a
    }

    override suspend fun loadMoreTopics(no_definitions: Boolean, page: Int): Response<ListTopic> {
        val a =    retroF.create(TopicsService::class.java).loadMoreTopicsWithCoroutines(no_definitions, page)
        println("" + retroF.toString())
        return a
    }
}