package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import android.net.Uri
import ventura.ferrer.josep.pere.proyectofinalandroid.BuildConfig

object ApiRoutes {

    fun signIn(username: String) =
        uriBuilder()
            .appendPath("users")
            .appendPath("${username}.json")
            .build()
            .toString()

    fun signUp() =
        uriBuilder()
            .appendPath("users")
            .build()
            .toString()

    fun getTopics() =
        uriBuilder()
            .appendPath("latest.json")
            .build()
            .toString()

    fun getPostsByTopicId(id:String) =
        uriBuilder()
            .appendPath("t")
            .appendPath("${id}.json")
            .build()
            .toString()

    fun getPostsByTopicIdPagination(idTopic:String, idPost:String) =
        uriBuilder()
            .appendPath("t")
            .appendPath("${idTopic}")
            .appendPath("posts.json")
            .appendQueryParameter("post_ids[]", idPost)
            .build()
            .toString()

    fun createTopic() =
        uriBuilder()
            .appendPath("posts.json")
            .build()
            .toString()

    private fun uriBuilder() =
        Uri.Builder()
            .scheme("https")
            .authority(BuildConfig.DiscourseDomain)


    fun getPosts() =
        uriBuilder()
            .appendPath("posts.json")
            .build()
            .toString()

}