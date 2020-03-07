package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login_fragment.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel

const val LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT"

class LoginFragment : Fragment(){

    var listener: LoginInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginInteractionListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLoginSignIn.setOnClickListener {
            println("sadadasdsasdaads")
            goToSignIn()
        }

        buttonLoginSignUp.setOnClickListener {
            goToSignUp()
        }
    }

    private fun goToSignUp(){
        listener?.onSignUpTap()
    }

    private fun goToSignIn(){
        var userName:String = editTextLoginUsername.text.toString()
        var password:String = editTextLoginPassword.text.toString()

        var loginModel:LoginModel = LoginModel(userName,password)

        listener?.onSignInTap(loginModel)
    }

    interface LoginInteractionListener {
        fun onSignInTap(loginrModel: LoginModel)
        fun onSignUpTap()
    }
}