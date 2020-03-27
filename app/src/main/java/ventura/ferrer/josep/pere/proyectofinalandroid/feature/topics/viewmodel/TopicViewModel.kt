    package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.viewmodel

    import android.content.Context
    import android.widget.Toast
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import kotlinx.coroutines.*
    import retrofit2.Response
    import ventura.ferrer.josep.pere.proyectofinalandroid.R
    import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.TopicsRepo
    import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.TopicsRepository
    import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.UserRepo
    import ventura.ferrer.josep.pere.proyectofinalandroid.database.TopicsNewEntity
    import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*
    import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.state.TopicManagementState
    import javax.inject.Inject
    import kotlin.coroutines.CoroutineContext


    class TopicViewModel @Inject constructor(private val topicsRepo: TopicsRepository) : ViewModel(), CoroutineScope {

        private val job = Job()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.IO


        private lateinit var _topicManagementState: MutableLiveData<TopicManagementState>
        val topicManagementState: LiveData<TopicManagementState>
            get() {
                if (!::_topicManagementState.isInitialized) {
                    _topicManagementState = MutableLiveData()
                }
                return _topicManagementState
            }

        // Navigate to topic detail view and display associated data
        fun onTopicSelected(topic: Topic) {
            _topicManagementState.value = TopicManagementState.GoToPosts(topic =  topic)
        }

        fun onGoToTopics(){
            _topicManagementState.value = TopicManagementState.OnGoToTopics
        }

        fun onGoToLatestNews(){
            _topicManagementState.value = TopicManagementState.OnGoToLatestNews
        }

        fun onPostSelected(latestPost: LatestPost){
            _topicManagementState.value = TopicManagementState.GoToPostsByLatestPost(latestPost)
        }

        fun navigateToCreateTopic(){
            _topicManagementState.value = TopicManagementState.NavigateToCreateTopic
        }

        fun onLogOut(){
            UserRepo.logOut()
            _topicManagementState.value = TopicManagementState.OnLogOut
        }

        fun onRetryButtonClicked() {
            _topicManagementState.value = TopicManagementState.Loading
            fetchTopicsAndHandleResponse()
        }

        fun onTopicsFragmentResumed() {
            _topicManagementState.value = TopicManagementState.Loading
            fetchTopicsAndHandleResponse()
        }

        fun onCreateTopicOptionClicked(createTopicModel: CreateTopicModel) {
            if (isFormValid(model = createTopicModel)) {

                val job = async{
                    val a = TopicsRepo.createTopic(createTopicModel)
                    a
                }

                launch(Dispatchers.Main) {
                    val response: Response<CreateTopicModelResponse> = job.await()

                    //todo deshabilitar loading
                    if (response.isSuccessful) {
                        response.body().takeIf { it != null }
                            ?.let {
                                val c: CreateTopicModelResponse = response.body()!!
                                _topicManagementState.value = TopicManagementState.CreateTopicCompleted
                                TopicManagementState.TopicCreatedSuccessfully()
                            }
                            ?: run { _topicManagementState.value = TopicManagementState.CreateTopicCompleted
                                //Todo TopicManagementState.RequestErrorReported(requestError = it)
                                }
                    } else {
                        //Todo TopicManagementState.RequestErrorReported(requestError = it)
                    }
                }
            } else {
                //Todo show error
            }

        }



        private fun isFormValid(model: CreateTopicModel) =
            with(model) { title.isNotEmpty() && raw.isNotEmpty() }

        private fun fetchTopicsAndHandleResponse() {
                val job = async {
                    val a = topicsRepo.getTopics()
                    a
                }

                launch(Dispatchers.Main) {
                    val response: Response<ListTopic> = job.await()

                    //todo deshabilitar loading
                    if (response.isSuccessful) {
                        response.body().takeIf { it != null }
                            ?.let {
                                val topics: ListTopic = response.body()!!
                                TopicsRepo.insertTopicsToDB(topics.topic_list.topics)
                                _topicManagementState.value = TopicManagementState.LoadTopicList(topicList = topics.topic_list.topics, loadMoreTopicsUrl = topics.topic_list.more_topics_url)
                            }
                            ?: run {
                                //Todo TopicManagementState.RequestErrorReported(requestError = it)
                            }
                    } else {
                        //Todo TopicManagementState.RequestErrorReported(requestError = it)
                    }
                }
        }

        fun loadMoreTopics(no_definitions: Boolean, page: Int){
            val job = async {
                val a = topicsRepo.loadMoreTopics(no_definitions, page)
                a
            }

            launch(Dispatchers.Main) {
                val response: Response<ListTopic> = job.await()

                //todo deshabilitar loading
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }
                        ?.let {
                            val topics: ListTopic = response.body()!!
                            val moreTopics = topics.topic_list.more_topics_url ?: ""
                            _topicManagementState.value = TopicManagementState.LoadMoreTopicList(topicList = topics.topic_list.topics, loadMoreTopicsUrl = moreTopics)
                        }
                        ?: run {
                            //Todo TopicManagementState.RequestErrorReported(requestError = it)
                        }
                } else {
                    //Todo TopicManagementState.RequestErrorReported(requestError = it)
                }
            }
        }
    }