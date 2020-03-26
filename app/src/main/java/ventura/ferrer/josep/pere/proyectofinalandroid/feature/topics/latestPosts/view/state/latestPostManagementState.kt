package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.state

import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RequestError
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPost


sealed class LatestPostManagementState {
    object Loading : LatestPostManagementState()
    class LoadPostList(val postList: List<LatestPost>) : LatestPostManagementState()
    class RequestErrorReported(val requestError: RequestError) : LatestPostManagementState()

}