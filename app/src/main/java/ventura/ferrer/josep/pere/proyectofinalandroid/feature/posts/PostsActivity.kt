package ventura.ferrer.josep.pere.proyectofinalandroid.feature.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.UserRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui.LoginActivity
import java.lang.IllegalArgumentException

const val EXTRA_TOPIC_ID = "topic_id"
const val EXTRA_TOPIC_TITLE = "topic_title"
const val TRANSACTION_CREATE_Post = "create_post"

class PostsActivity : AppCompatActivity(),
    PostsFragment.PostsInteractionListener,
    CreatePostFragment.CreatePostInteractionListener {

    var topicId = ""
    var topicTitle = ""
    var postsFragment: PostsFragment? =
        PostsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        setTitle(R.string.posts)

        topicId = intent.getStringExtra(EXTRA_TOPIC_ID)
        topicTitle = intent.getStringExtra(EXTRA_TOPIC_TITLE)

        if(topicId != null && topicId.isNotEmpty()) {
            if(savedInstanceState == null){
                val bundle = Bundle()
                bundle.putString("idTopic",topicId)
                postsFragment = PostsFragment()
                postsFragment!!.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, postsFragment!!)
                    .commit()


            }

        } else {
            throw IllegalArgumentException("You should provide an id for the topic")
        }
    }

    override fun onLogOut() {
        UserRepo.logOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPostCreated() {
        supportFragmentManager.popBackStack()


    }

    override fun createPost(idTopic:String) {
        val bundle = Bundle()
        bundle.putString("idTopic",idTopic)
        bundle.putString(EXTRA_TOPIC_TITLE,topicTitle)
        val postsFragment =
            CreatePostFragment()
        postsFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, postsFragment)
            .addToBackStack(TRANSACTION_CREATE_Post)
            .commit()
    }

}
