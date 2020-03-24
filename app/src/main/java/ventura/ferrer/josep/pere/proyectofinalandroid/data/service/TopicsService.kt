package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.*
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModelResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ListTopic
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.PrivateMessageListResponse

interface TopicsService {
    @POST("posts")
    suspend fun loginUserWithCoroutines(@Body createTopicModel: CreateTopicModel): Response<CreateTopicModelResponse>

    @GET("latest.json")
    suspend fun getTopics(): Response<ListTopic>

    @GET("/latest.json")
    suspend fun loadMoreTopicsWithCoroutines(@Query("no_definitions") no_definitions: Boolean, @Query("page") page: Int): Response<ListTopic>
}
