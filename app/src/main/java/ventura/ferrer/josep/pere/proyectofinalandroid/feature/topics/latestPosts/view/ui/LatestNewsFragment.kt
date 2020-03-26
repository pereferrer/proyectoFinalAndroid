package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_posts.parentLayout
import kotlinx.android.synthetic.main.fragment_posts.swiperefreshPosts
import kotlinx.android.synthetic.main.latest_news.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RequestError
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LatestPost
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.latestPosts.view.adapter.LatestNewsAdapter


const val LATEST_NEWS_FRAGMENT_TAG = "LATEST_NEWS_FRAGMENT"


class LatestNewsFragment : Fragment(){
    var listener: LatestNewsInteractionListener? = null
    lateinit var adapter: LatestNewsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("onAttach")
        if (context is LatestNewsInteractionListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        println("onCreate")
        adapter =
            LatestNewsAdapter {
                goToPosts(it)
            }
    }

    private fun goToPosts(it: LatestPost) {
        listener?.onPostSelected(it)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts, menu)//Todo cambiar menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView")

        return inflater.inflate(R.layout.latest_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("onViewCreated")

        listLatestNews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listLatestNews.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listLatestNews.adapter = adapter

        swiperefreshPosts.setOnRefreshListener {
            listener?.onRetryLatestNewsButtonClicked()
            swiperefreshPosts.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }
    }

    override fun onResume() {
        super.onResume()
        listener?.onRetryLatestNewsButtonClicked()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_log_out -> listener?.onLogOut()
        }

        return super.onOptionsItemSelected(item)
    }

    fun loadLatestNews(latestNewsList: List<LatestPost>) {
        println("loadLatestNews")
        enableLoading(false)
        adapter.setLatestNews(posts = latestNewsList)
    }

    fun enableLoading(enabled: Boolean) {
        println("enableLoading")

        Log.d("viewRetryLatest_news","viewRetryLatest_news")

        viewRetryLatest_news.visibility = View.INVISIBLE

        if (enabled) {
            listLatestNews.visibility = View.INVISIBLE
            viewLoadingLatest_news.visibility = View.VISIBLE
        } else {
            listLatestNews.visibility = View.VISIBLE
            viewLoadingLatest_news.visibility = View.INVISIBLE
        }
    }

    fun handleRequestError(requestError: RequestError) {
        listLatestNews.visibility = View.INVISIBLE
        viewRetryLatest_news.visibility = View.VISIBLE

        val message = if (requestError.messageResId != null)
            getString(requestError.messageResId)
        else if (requestError.message != null)
            requestError.message
        else
            getString(R.string.error_request_default)

        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show()
    }


    interface LatestNewsInteractionListener {
        fun onLogOut()
        fun onPostSelected(latestPost: LatestPost)
        fun onRetryLatestNewsButtonClicked()
    }
}