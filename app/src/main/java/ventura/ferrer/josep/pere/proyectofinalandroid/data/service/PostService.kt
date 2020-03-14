package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.GET
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPostRetrofit

interface PostService {

    @GET("posts.json")
    suspend fun getPostsAcrossTopics(): Response<LatestPostRetrofit>
}