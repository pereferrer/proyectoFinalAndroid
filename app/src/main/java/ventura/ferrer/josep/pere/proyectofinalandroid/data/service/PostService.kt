package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*

interface PostService {

    @GET("posts.json")
    suspend fun getPostsAcrossTopics(): Response<LatestPostRetrofit>
}