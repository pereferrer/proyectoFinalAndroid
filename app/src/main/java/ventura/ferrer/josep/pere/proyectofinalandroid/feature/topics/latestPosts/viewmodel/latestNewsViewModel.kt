package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PostsRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PostsRepository
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPostRetrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.state.LatestPostManagementState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LatestNewsViewModel @Inject constructor(private val postRepo: PostsRepository) : ViewModel(),
    CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private lateinit var _latestPostManagementState: MutableLiveData<LatestPostManagementState>
    val latestNewsManagementState: LiveData<LatestPostManagementState>
        get() {
            if (!::_latestPostManagementState.isInitialized) {
                _latestPostManagementState = MutableLiveData()
            }
            return _latestPostManagementState
        }

    fun onRetryButtonClicked(context: Context) {
        _latestPostManagementState.value = LatestPostManagementState.Loading
        fetchLatestPostsAndHandleResponse(context = context)
    }

    private fun fetchLatestPostsAndHandleResponse(context: Context) {
        if(isOnline(context = context)){
            val job = async {
                val a = postRepo.getPostsAcrossTopics()
                a
            }

            launch(Dispatchers.Main) {
                val response: Response<LatestPostRetrofit> = job.await()

                //todo deshabilitar loading
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }
                        ?.let {
                            PostsRepo.insertAllLatestNew(it.latest_posts)
                            _latestPostManagementState.value = LatestPostManagementState.LoadPostList(postList = it.latest_posts)
                        }
                        ?: run {
                            PostsRepo.getLatestNewFromDB(
                                { latestPost ->
                                    _latestPostManagementState.value = LatestPostManagementState.LoadPostList(postList = latestPost)
                                }, { error ->
                                    _latestPostManagementState.value = LatestPostManagementState.showLatestNewsErrorMessage(error)
                                })
                        }
                } else {
                    PostsRepo.getLatestNewFromDB(
                        { latestPost ->
                            _latestPostManagementState.value = LatestPostManagementState.LoadPostList(postList = latestPost)
                        }, { error ->
                            _latestPostManagementState.value = LatestPostManagementState.showLatestNewsErrorMessage(error)
                        })
                }
            }
        }else{
            PostsRepo.getLatestNewFromDB(
                { latestPost ->
                    _latestPostManagementState.value = LatestPostManagementState.LoadPostList(postList = latestPost)
                }, { error ->
                    _latestPostManagementState.value = LatestPostManagementState.showLatestNewsErrorMessage(error)
                })
        }

    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
}