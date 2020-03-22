package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.state

import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RequestError
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPost
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic


sealed class TopicManagementState {
    class GoToPosts(val topic: Topic): TopicManagementState()
    class GoToPostsByLatestPost(val latestPost: LatestPost): TopicManagementState()
    class LoadTopicList(val topicList: List<Topic>) : TopicManagementState()
    object OnGoToTopics : TopicManagementState()
    object OnGoToLatestNews : TopicManagementState()
    object NavigateToCreateTopic: TopicManagementState()
    object OnLogOut: TopicManagementState()
    object CreateTopicCompleted : TopicManagementState()
    object Loading : TopicManagementState()
    class TopicCreatedSuccessfully(val msg: String) : TopicManagementState()
    class RequestErrorReported(val requestError: RequestError) : TopicManagementState()

}