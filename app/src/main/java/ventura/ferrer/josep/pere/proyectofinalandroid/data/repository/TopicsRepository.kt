package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModelResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ListTopic

interface TopicsRepository {
    suspend fun getTopics(): Response<ListTopic>
    suspend fun createTopic(createTopicModel: CreateTopicModel): Response<CreateTopicModelResponse>
}