package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.di.DaggerApplicationGraph
import ventura.ferrer.josep.pere.proyectofinalandroid.di.UtilsModule
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state.LoginManagementState
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.viewmodel.LoginViewModel
import java.util.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), RegisterFragment.RegisterInteractionListener, LoginFragment.LoginInteractionListener, DetailUserFragment.DetailUserInteractionListener {

    @Inject
    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        DaggerApplicationGraph.builder()
            .utilsModule(UtilsModule(applicationContext)).build()
            .inject(this)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,
                    DetailUserFragment(),
                    DETAIL_USER_FRAGMENT_TAG).commit()
        }

        initModel()
    }

    private fun initModel(){
        loginViewModel.registerManagementState.observe(this, Observer{state->
            when (state){
                is LoginManagementState.FormErrorReported -> showError(msg = state.errorMsg)
                is LoginManagementState.RequestErrorReported -> showError(msg = state.errorMsg)
                is LoginManagementState.DetailUserSuccessfully -> showDetailUser(detailUser = state.detailUser)
                is LoginManagementState.PrivateMessageListSuccessfully -> showPrivateMessageList(topics = state.topics)
            }
        })
    }

    private fun showPrivateMessageList(topics: List<Topic>) {
        getDetailUserFragmentIfAvailableOrNull()?.run {
            showData(topics = topics)
        }
    }

    private fun showDetailUser(detailUser: DetailUserResponse) {
        getDetailUserFragmentIfAvailableOrNull()?.run {
            showData(detailUserResponse = detailUser)
        }
    }

    private fun getDetailUserFragmentIfAvailableOrNull(): DetailUserFragment? {
        val fragment: Fragment? =
            supportFragmentManager.findFragmentByTag(DETAIL_USER_FRAGMENT_TAG)

        return if (fragment != null && fragment.isVisible) {
            fragment as DetailUserFragment

        } else {
            null
        }
    }

    override fun onSignUpTap(registerModel: RegisterModel) {
        loginViewModel.onSignUpTap(context = this, registerModel = registerModel)
    }

    //Esta es la del Login
    override fun onSignInTap(loginrModel: LoginModel) {
        loginViewModel.onSignInTap(context = this, loginModel = loginrModel)
    }

    //Esta es la del Registro
    override fun onSignInTap() {
        supportFragmentManager.popBackStack()
    }

    //Esta es la del Login
    override fun onSignUpTap() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                RegisterFragment(),
                REGISTER_FRAGMENT_TAG
            )
            .addToBackStack(LOGIN_FRAGMENT_TAG)
            .commit()
    }

    override fun onRecoverPassword(forgotPassword: ForgotPasswordModel) {
        loginViewModel.onRecoverPassword(context = this, forgotPassword = forgotPassword)
    }

    private fun showError(msg: String) {
        Snackbar.make(fragmentContainer, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun getDetailUser() {
        loginViewModel.ongetDetailUser(context = this)
    }

    override fun getPrivateMessageList() {
        loginViewModel.getPrivateMessageList(context = this)
    }
}


