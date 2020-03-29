package ventura.ferrer.josep.pere.proyectofinalandroid.feature.feature.topics.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_topics.*
import kotlinx.android.synthetic.main.content_main.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RequestError
import ventura.ferrer.josep.pere.proyectofinalandroid.di.DaggerApplicationGraph
import ventura.ferrer.josep.pere.proyectofinalandroid.di.UtilsModule
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPost
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.User
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui.LoginActivity
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts.EXTRA_TOPIC_ID
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts.EXTRA_TOPIC_TITLE
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts.PostsActivity
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.state.LatestPostManagementState
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.ui.LATEST_NEWS_FRAGMENT_TAG
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.ui.LatestNewsFragment
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.viewmodel.LatestNewsViewModel
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.state.TopicManagementState
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui.CreateTopicFragment
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui.TOPICS_FRAGMENT_TAG
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui.TopicsFragment
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.viewmodel.TopicViewModel
import javax.inject.Inject

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity : AppCompatActivity(),
    TopicsFragment.TopicsInteractionListener, CreateTopicFragment.CreateTopicInteractionListener, NavigationView.OnNavigationItemSelectedListener,
    LatestNewsFragment.LatestNewsInteractionListener {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    @Inject
    lateinit var topicViewModel: TopicViewModel
    @Inject
    lateinit var latestNewsViewModel: LatestNewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        DaggerApplicationGraph.builder()
            .utilsModule(UtilsModule(applicationContext)).build()
            .inject(this)

        initModel()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,
                    TopicsFragment(),
                    TOPICS_FRAGMENT_TAG
                )
                .commit()
        }

        initNavigationDrawer()
    }

    private fun initModel() {
        topicViewModel.topicManagementState.observe(this, Observer { state ->
            when (state) {
                TopicManagementState.Loading -> enableLoadingView()
                is TopicManagementState.showTopicsErrorMessage -> showTopicsErrorMessage(error = state.error)
                is TopicManagementState.LoadTopicList -> loadTopicList(list = state.topicList, loadMoreTopicsUrl = state.loadMoreTopicsUrl,
                    users = state.users
                )
                is TopicManagementState.LoadMoreTopicList -> loadMoreTopicsList(list = state.topicList, loadMoreTopicsUrl = state.loadMoreTopicsUrl,
                    users = state.users)
                is TopicManagementState.GoToPosts -> goToPosts(state.topic)
                is TopicManagementState.TopicCreatedSuccessfully -> showMessage()
                is TopicManagementState.RequestErrorReported -> showRequestError(error = state.requestError)
                is TopicManagementState.GoToPostsByLatestPost -> goToPostsByLatestPost(state.latestPost)
                TopicManagementState.OnGoToTopics -> onGoToTopics()
                TopicManagementState.NavigateToCreateTopic -> navigateToCreateTopic()
                TopicManagementState.OnLogOut -> exit()
                TopicManagementState.OnGoToLatestNews -> onGoToLatestNews()
                TopicManagementState.CreateTopicCompleted -> {
                    onTopicCreated()
                }
            }
        })

        latestNewsViewModel.latestNewsManagementState.observe(this, Observer { state ->
            when(state){
                is LatestPostManagementState.RequestErrorReported -> showLatestNewsRequestError(error = state.requestError)
                is LatestPostManagementState.showLatestNewsErrorMessage -> showLatestNewsErrorMessage(error = state.error)
                is LatestPostManagementState.LoadPostList -> loadpostList(list = state.postList)
                LatestPostManagementState.Loading -> enableLatestNewsLoadingView()
            }
        })
    }

    private fun showError(msg: String) {
        Snackbar.make(drawer_layout, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun loadMoreTopicsList(list: List<Topic>, loadMoreTopicsUrl: String, users:List<User>?) {

        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            loadTopicList(topicList = list, loadMoreTopicsUrl = loadMoreTopicsUrl, users = users)
        }
    }

    private fun initNavigationDrawer(){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        println("onNavigationItemSelected")

        when (item.itemId) {
            R.id.nav_topics -> {
                topicViewModel.onGoToTopics()
                AppTitle.setText(R.string.topics);
            }
            R.id.nav_latest_news -> {
                println("nav_latest_news")

                topicViewModel.onGoToLatestNews()
                AppTitle.setText(R.string.latest_news);
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onTopicSelected(topic: Topic) {
        topicViewModel.onTopicSelected(topic = topic)
    }

    override fun onGoToCreateTopic() {
        topicViewModel.navigateToCreateTopic()
    }

    override fun onLogOut() {
        topicViewModel.onLogOut()
    }

    override fun onPostSelected(latestPost: LatestPost) {
        topicViewModel.onPostSelected(latestPost)
    }

    override fun loadMoreTopics(no_definitions: Boolean, page: Int) {
        topicViewModel.loadMoreTopics(no_definitions = no_definitions, page = page)
    }

    override fun onTopicsFragmentResumed() {
        AppTitle.setText(R.string.topics);
        topicViewModel.onTopicsFragmentResumed(context = applicationContext)
    }

    override fun onRetryButtonClicked() {
        topicViewModel.onRetryButtonClicked(context = applicationContext)
    }

    override fun onRetryLatestNewsButtonClicked(){
        latestNewsViewModel.onRetryButtonClicked(context = this)
    }

    private fun onGoToTopics() {
        AppTitle.setText(R.string.topics);
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                TopicsFragment(),
                TOPICS_FRAGMENT_TAG
            )
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    private fun onGoToLatestNews(){
        println("onGoToLatestNews")
        AppTitle.setText(R.string.latest_news);
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                LatestNewsFragment(),
                LATEST_NEWS_FRAGMENT_TAG
            )
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }


    override fun onTopicCreated() {
        AppTitle.setText(R.string.topics);
        supportFragmentManager.popBackStack()
    }

    override fun onCreateTopicOptionClicked(model: CreateTopicModel) {
        topicViewModel.onCreateTopicOptionClicked(createTopicModel = model)
    }

    private fun goToPosts(topic: Topic) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topic.id)
        intent.putExtra(EXTRA_TOPIC_TITLE, topic.title)
        startActivity(intent)
    }


    private fun getTopicsFragmentIfAvailableOrNull(): TopicsFragment? {
        val fragment: Fragment? =
            supportFragmentManager.findFragmentByTag(TOPICS_FRAGMENT_TAG)

        return if (fragment != null && fragment.isVisible) {
            fragment as TopicsFragment
        } else {
            null
        }
    }

    private fun getLatestNewsFragmentIfAvailableOrNull(): LatestNewsFragment? {
        val fragment: Fragment? =
            supportFragmentManager.findFragmentByTag(LATEST_NEWS_FRAGMENT_TAG)

        return if (fragment != null && fragment.isVisible) {
            fragment as LatestNewsFragment
        } else {
            null
        }
    }

    private fun enableLoadingView() {
        getTopicsFragmentIfAvailableOrNull()?.enableLoading( true)
    }

    private fun showRequestError(error: RequestError) {
        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            handleRequestError(requestError = error)
        }
    }

    private fun enableLatestNewsLoadingView() {
        getLatestNewsFragmentIfAvailableOrNull()?.enableLoading( true)
    }


    private fun loadTopicList(list: List<Topic>, loadMoreTopicsUrl:String, users:List<User>?) {
        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            loadTopicList(topicList = list, loadMoreTopicsUrl = loadMoreTopicsUrl, users = users)
        }
    }

    private fun showLatestNewsRequestError(error: RequestError) {
        getLatestNewsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            handleRequestError(requestError = error)
        }
    }

    private fun showLatestNewsErrorMessage(error: String) {
        getLatestNewsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            showError(errorMsg = error)
        }
    }

    private fun showTopicsErrorMessage(error: String) {
        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            showError(errorMsg = error)
        }
    }

    private fun loadpostList(list: List<LatestPost>) {
        print("sasdsasasad")
        Log.d("loadpostList","loadpostList")
        getLatestNewsFragmentIfAvailableOrNull()?.run {
            Log.d("enabled","enabled")

            enableLoading(enabled = false)
            loadLatestNews(latestNewsList = list)
        }
    }

    private fun goToPostsByLatestPost(latestPost: LatestPost) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, latestPost.id)
        intent.putExtra(EXTRA_TOPIC_TITLE, latestPost.topic_title)
        startActivity(intent)
    }

    private fun navigateToCreateTopic(){
        AppTitle.setText(R.string.create_topic);
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                CreateTopicFragment()
            )
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    private fun exit(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMessage() {
        Toast.makeText(this, R.string.message_topic_created, Toast.LENGTH_LONG).show()
    }
}