package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreatePostToTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreatePostToTopicResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPostRetrofit

interface PostsRepository {

    suspend fun getPostsAcrossTopics(): Response<LatestPostRetrofit>
}