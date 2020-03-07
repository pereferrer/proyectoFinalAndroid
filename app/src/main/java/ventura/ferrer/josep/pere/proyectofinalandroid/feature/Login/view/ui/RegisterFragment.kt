package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel

const val REGISTER_FRAGMENT_TAG = "REGISTER_FRAGMENT"

class RegisterFragment : Fragment(){

    var listener: RegisterInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegisterInteractionListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonRegisterSignUp.setOnClickListener {
            goToSignUp()
        }

        buttonRegisterSignIn.setOnClickListener {
            goToSignIn()
        }

    }

    private fun goToSignUp(){
        var name:String = editTextRegisterName.text.toString()
        var username:String = editTextRegisterUsername.text.toString()
        var mail:String = editTextRegisterMail.text.toString()
        var password:String = editTextRegisterPassword.text.toString()

        var registerModel: RegisterModel = RegisterModel(name, mail, password,username)

        listener?.onSignUpTap(registerModel)
    }

    private fun goToSignIn(){
        listener?.onSignInTap()
    }

    interface RegisterInteractionListener {
        fun onSignUpTap(registerModel: RegisterModel)
        fun onSignInTap()
    }


}