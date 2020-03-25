package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.DetailUserResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic

const val DETAIL_USER_FRAGMENT_TAG = "DETAIL_USER_FRAGMENT"

class DetailUserFragment : Fragment(){

    var listener: DetailUserInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailUserInteractionListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetailUser()
        getPrivateMessageList()
    }

    private fun getDetailUser(){
        listener?.getDetailUser()//Todo
    }

    private fun getPrivateMessageList(){
        listener?.getPrivateMessageList()//Todo
    }

    fun showData(detailUserResponse: DetailUserResponse){
    }

    fun showData(topics: List<Topic>){
    }

    interface DetailUserInteractionListener {
        fun getDetailUser()
        fun getPrivateMessageList()
    }
}