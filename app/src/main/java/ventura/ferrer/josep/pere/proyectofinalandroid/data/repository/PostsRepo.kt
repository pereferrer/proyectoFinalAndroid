package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository


import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.*
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*

object PostsRepo : PostsRepository{

    lateinit var ctx: Context
    lateinit var retroF: Retrofit


    fun getPosts(idPost:String,
                 context: Context,
                 onSuccess:(List<Post>) ->Unit,
                 onError:(RequestError)->Unit)
    {

        val username = UserRepo.getUsername()
        println("El username es?: " + username)
        val request = UserRequest(
            username,
            Request.Method.GET,
            ApiRoutes.getPostsByTopicId(idPost),
            null,
            {
                it?.let {
                    onSuccess.invoke(
                        Post.parsePosts(
                            it
                        )
                    )
                }

                if (it == null)
                    onError.invoke(
                        RequestError(
                            messageResId = R.string.error_invalid_response
                        )
                    )
            },
            {
                it.printStackTrace()
                if (it is NetworkError)
                    onError.invoke(
                        RequestError(
                            messageResId = R.string.error_network
                        )
                    )
                else
                    onError.invoke(
                        RequestError(
                            it
                        )
                    )
            })

        ApiRequestQueue.getRequestQueue(context)
            .add(request)
    }

    fun getNextPost(idTopic:String,
                    idPost:String,
                    context: Context,
                    onSuccess:(List<Post>) ->Unit,
                    onError:(RequestError)->Unit)
    {

        val username = UserRepo.getUsername()
        println("El username es?: " + username)
        val request = UserRequest(
            username,
            Request.Method.GET,
            ApiRoutes.getPostsByTopicIdPagination(idTopic,idPost),
            null,
            {
                it?.let {
                    onSuccess.invoke(
                        Post.parsePosts(
                            it
                        )
                    )
                }

                if (it == null)
                    onError.invoke(
                        RequestError(
                            messageResId = R.string.error_invalid_response
                        )
                    )
            },
            {
                it.printStackTrace()
                if (it is NetworkError)
                    onError.invoke(
                        RequestError(
                            messageResId = R.string.error_network
                        )
                    )
                else
                    onError.invoke(
                        RequestError(
                            it
                        )
                    )
            })
        println("cdsadsadads" +request + "dsa" + idTopic + "dcassd" + idPost)

        ApiRequestQueue.getRequestQueue(context)
            .add(request)
    }


    override suspend fun getPostsAcrossTopics(): Response<LatestPostRetrofit> {
        val a =  retroF.create(PostService::class.java).getPostsAcrossTopics()
        println("He acabat retrofit")
        return a
    }



    fun createPost(
        context: Context,
        model: CreatePostModel,
        onSuccess: (CreatePostModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val username = UserRepo.getUsername()
        val request = UserRequest(
            username,
            Request.Method.POST,
            ApiRoutes.createTopic(),
            model.toJson(),
            {
                it?.let {
                    onSuccess.invoke(model)
                }

                if (it == null)
                    onError.invoke(
                        RequestError(
                            messageResId = R.string.error_invalid_response
                        )
                    )
            },
            {
                it.printStackTrace()

                if (it is ServerError && it.networkResponse.statusCode == 422) {
                    val body = String(it.networkResponse.data, Charsets.UTF_8)
                    val jsonError = JSONObject(body)
                    val errors = jsonError.getJSONArray("errors")
                    var errorMessage = ""

                    for (i in 0 until errors.length()) {
                        errorMessage += "${errors[i]} "
                    }

                    onError.invoke(
                        RequestError(
                            it,
                            message = errorMessage
                        )
                    )

                } else if (it is NetworkError)
                    onError.invoke(
                        RequestError(
                            it,
                            messageResId = R.string.error_network
                        )
                    )
                else
                    onError.invoke(
                        RequestError(
                            it
                        )
                    )
            }
        )

        ApiRequestQueue.getRequestQueue(context)
            .add(request)
    }
}


