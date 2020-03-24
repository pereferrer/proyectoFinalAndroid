package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.ui


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_topics.*
import kotlinx.android.synthetic.main.fragment_topics.parentLayout
import kotlinx.android.synthetic.main.view_retry.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RequestError
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.adapter.TopicsAdapter


const val TOPICS_FRAGMENT_TAG = "TOPICS_FRAGMENT"

class TopicsFragment : Fragment() {

    var listener: TopicsInteractionListener? = null
    lateinit var adapter: TopicsAdapter
    var loadMoreTopicsUrl:String = ""
    var currentTopics = mutableListOf<Topic>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopicsInteractionListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        adapter = TopicsAdapter {
            goToPosts(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topics, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_topics, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listTopics.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listTopics.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listTopics.adapter = adapter

        buttonCreate.setOnClickListener {
            goToCreateTopic()
        }

        buttonRetry.setOnClickListener {
            listener?.onRetryButtonClicked()
        }

        swiperefreshTopics.setOnRefreshListener {
            listener?.onRetryButtonClicked()
            swiperefreshTopics.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }

        topics_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })


        listTopics.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val linearLayoutManager = LinearLayoutManager(context)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!listTopics.canScrollVertically(1)){
                    onScrolledToBottom()
                }
            }

            private fun onScrolledToBottom() {
                println("Estoy en el bottom")
                loadMoreTopics()

            }
        })
    }

    override fun onResume() {
        super.onResume()

        listener?.onTopicsFragmentResumed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_log_out -> listener?.onLogOut()
        }
        return super.onOptionsItemSelected(item)
    }



    fun enableLoading(enabled: Boolean) {
        viewRetry.visibility = View.INVISIBLE
        if (enabled) {
            listTopics.visibility = View.INVISIBLE
            buttonCreate.hide()
            viewLoading.visibility = View.VISIBLE
        } else {
            listTopics.visibility = View.VISIBLE
            buttonCreate.show()
            viewLoading.visibility = View.INVISIBLE
        }
    }


    fun loadTopicList(topicList: List<Topic>, loadMoreTopicsUrl:String) {
        currentTopics.addAll(topicList)
        this.loadMoreTopicsUrl = loadMoreTopicsUrl
        enableLoading(false)
        adapter.setTopics(topics = topicList)
    }

    fun loadMoreTopicList(topicList: List<Topic>, loadMoreTopicsUrl:String) {
        currentTopics.addAll(topicList)
        this.loadMoreTopicsUrl = loadMoreTopicsUrl
        enableLoading(false)
        adapter.setTopics(topics = topicList)
    }

    fun loadMoreTopics(){
        if(loadMoreTopicsUrl != ""){
            println("Estoy en el loadMoreTopics")
            enableLoading(true)
            val page = loadMoreTopicsUrl.substring(33).toInt()
            val definitions = loadMoreTopicsUrl.substring(27).toBoolean()
            listener?.loadMoreTopics(definitions, page)
        }else{
            Snackbar.make(parentLayout, getString(R.string.no_more_topics_to_load), Snackbar.LENGTH_LONG).show()
        }
    }


    fun handleRequestError(requestError: RequestError) {
        listTopics.visibility = View.INVISIBLE
        viewRetry.visibility = View.VISIBLE

        val message = if (requestError.messageResId != null)
            getString(requestError.messageResId)
        else if (requestError.message != null)
            requestError.message
        else
            getString(R.string.error_request_default)

        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun goToCreateTopic() {
        listener?.onGoToCreateTopic()
    }

    private fun goToPosts(it: Topic) {
        listener?.onTopicSelected(it)
    }

    interface TopicsInteractionListener {
        fun onRetryButtonClicked()
        fun onTopicsFragmentResumed()
        fun onTopicSelected(topic: Topic)
        fun onGoToCreateTopic()
        fun onLogOut()
        fun loadMoreTopics(no_definitions: Boolean, page: Int)
    }

}


class ScrollAwareFABBehavior(context: Context, attrs: AttributeSet): FloatingActionButton.Behavior(context, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton, directTargetChild: View, target: View,
                                     axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout,
            child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
            dyUnconsumed, type)

        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                @SuppressLint("RestrictedApi")
                override fun onHidden(fab: FloatingActionButton) {
                    super.onHidden(fab)
                    fab.visibility = View.INVISIBLE
                }
            })
            // } else if (dyUnconsumed < 0 && child.visibility != View.VISIBLE) {
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show()
        }
    }
}
