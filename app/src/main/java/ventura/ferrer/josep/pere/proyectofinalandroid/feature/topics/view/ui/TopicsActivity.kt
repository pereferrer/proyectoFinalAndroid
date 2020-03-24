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
import kotlinx.android.synthetic.main.content_main.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RequestError
import ventura.ferrer.josep.pere.proyectofinalandroid.di.DaggerApplicationGraph
import ventura.ferrer.josep.pere.proyectofinalandroid.di.UtilsModule
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.CreateTopicModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPost
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui.LoginActivity
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts.EXTRA_TOPIC_ID
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts.EXTRA_TOPIC_TITLE
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts.PostsActivity
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.state.TopicManagementState
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui.CreateTopicFragment
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui.TOPICS_FRAGMENT_TAG
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui.TopicsFragment
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.viewmodel.TopicViewModel
import javax.inject.Inject

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity : AppCompatActivity(),
    TopicsFragment.TopicsInteractionListener, CreateTopicFragment.CreateTopicInteractionListener, NavigationView.OnNavigationItemSelectedListener {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    @Inject
    lateinit var topicViewModel: TopicViewModel


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
                is TopicManagementState.LoadTopicList -> loadTopicList(list = state.topicList, loadMoreTopicsUrl = state.loadMoreTopicsUrl)
                is TopicManagementState.LoadMoreTopicList -> loadMoreTopicsList(list = state.topicList, loadMoreTopicsUrl = state.loadMoreTopicsUrl)
                is TopicManagementState.GoToPosts -> goToPosts(state.topic)
                is TopicManagementState.TopicCreatedSuccessfully -> showMessage(msg = state.msg)
                is TopicManagementState.RequestErrorReported -> showRequestError(error = state.requestError)
                TopicManagementState.OnGoToTopics -> onGoToTopics()
                TopicManagementState.NavigateToCreateTopic -> navigateToCreateTopic()
                TopicManagementState.OnLogOut -> exit()
                TopicManagementState.CreateTopicCompleted -> {
                    onTopicCreated()
                }
            }
        })
    }

    private fun loadMoreTopicsList(list: List<Topic>, loadMoreTopicsUrl: String) {
        println("He vuelto a la activity para volver al fragment")

        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            loadTopicList(topicList = list, loadMoreTopicsUrl = loadMoreTopicsUrl)
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
        when (item.itemId) {
            R.id.nav_topics -> {
                topicViewModel.onGoToTopics(this)
                AppTitle.setText(R.string.topics);
            }
            R.id.nav_latest_news -> {
                topicViewModel.onGoToLatestNews(this)
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
        topicViewModel.onLogOut(this)
    }

    override fun loadMoreTopics(no_definitions: Boolean, page: Int) {
        println("Estoy en el bottom loadMoreTopics activity")

        topicViewModel.loadMoreTopics(context = this, no_definitions = no_definitions, page = page)
    }

    override fun onTopicsFragmentResumed() {
        AppTitle.setText(R.string.topics);
        topicViewModel.onTopicsFragmentResumed(context = this)
    }

    override fun onRetryButtonClicked() {
        topicViewModel.onRetryButtonClicked(context = this)
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


    override fun onTopicCreated() {
        AppTitle.setText(R.string.topics);
        supportFragmentManager.popBackStack()
    }

    override fun onCreateTopicOptionClicked(model: CreateTopicModel) {
        topicViewModel.onCreateTopicOptionClicked(context = this, createTopicModel = model)
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

    private fun enableLoadingView() {
        getTopicsFragmentIfAvailableOrNull()?.enableLoading( true)
    }

    private fun showRequestError(error: RequestError) {
        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            handleRequestError(requestError = error)
        }
    }


    private fun loadTopicList(list: List<Topic>, loadMoreTopicsUrl:String) {
        getTopicsFragmentIfAvailableOrNull()?.run {
            enableLoading(enabled = false)
            loadTopicList(topicList = list, loadMoreTopicsUrl = loadMoreTopicsUrl)
        }
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

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}