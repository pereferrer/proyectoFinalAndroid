package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.di.DaggerApplicationGraph
import ventura.ferrer.josep.pere.proyectofinalandroid.di.UtilsModule
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.viewmodel.LoginViewModel
import java.util.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), RegisterFragment.RegisterInteractionListener, LoginFragment.LoginInteractionListener {

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
                    LoginFragment(),
                    LOGIN_FRAGMENT_TAG).commit()
        }

        initModel()
    }

    private fun initModel(){
        loginViewModel.registerManagementState.observe(this, Observer{state->
            when (state){

            }
        })
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
}


