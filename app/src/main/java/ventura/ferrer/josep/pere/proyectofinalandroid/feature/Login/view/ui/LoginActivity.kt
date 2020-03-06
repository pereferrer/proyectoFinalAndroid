package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.di.DaggerApplicationGraph
import ventura.ferrer.josep.pere.proyectofinalandroid.di.UtilsModule
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.viewmodel.LoginViewModel
import java.util.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), RegisterFragment.RegisterInteractionListener {

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
                    RegisterFragment(),
                    REGISTER_FRAGMENT_TAG).commit()
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

    override fun onSignInTap() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}


