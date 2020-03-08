package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.LoginRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state.LoginManagementState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(private val registerRepo: RegisterRepo, private  val loginRepo: LoginRepo):ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private  lateinit var _registerManagementState: MutableLiveData<LoginManagementState>
    val registerManagementState: LiveData<LoginManagementState>
        get() {
            if (!::_registerManagementState.isInitialized) {
                _registerManagementState = MutableLiveData()
            }
            return _registerManagementState
        }

    fun onSignUpTap(context: Context, registerModel: RegisterModel){
        if(isFormValid(model = registerModel)){
            val job = async{
                val a = RegisterRepo.registerNewUser(registerModel)
                println("Done async")
                a
            }

            launch(Dispatchers.Main) {
                val response: Response<RegisterModelResponse> = job.await()
                println("Done await")

                //todo deshabilitar loading
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }
                        ?.let {
                            val c: RegisterModelResponse = response.body()!!
                            if(c.success){
                                _registerManagementState.value = LoginManagementState.RegisterUserCompleted
                                LoginManagementState.UserRegistredSuccessfully(msg = context.getString(R.string.user_created))
                            }else{
                                _registerManagementState.value = LoginManagementState.RequestErrorReported(c.message)
                            }
                        }
                        ?: run { _registerManagementState.value = LoginManagementState.RegisterUserCompleted
                            _registerManagementState.value = LoginManagementState.RequestErrorReported("Opps! User not created")
                        }
                } else {
                    _registerManagementState.value = LoginManagementState.RequestErrorReported(context.getString(R.string.try_it_again))
                }
                println("Done launch")
            }
            println("Done!")
        } else {
            _registerManagementState.value = LoginManagementState.FormErrorReported(context.getString(
                R.string.sign_up_field_empty))
        }

    }

    fun onSignInTap(context: Context, loginModel: LoginModel){
        if(isFormValid(model = loginModel)){

            val job = async{
                val a = LoginRepo.loginUser(loginModel)
                println("Done async")
                a
            }

            launch(Dispatchers.Main) {
                val response: Response<LoginModelResponse> = job.await()
                println("Done await")

                //todo deshabilitar loading
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }
                        ?.let {
                            val c: LoginModelResponse = response.body()!!
                            if(c.user != null){
                                _registerManagementState.value = LoginManagementState.LoginUserCompleted
                                LoginManagementState.UserLoggedSuccessfully(msg = context.getString(R.string.user_logged))
                            }else{
                                _registerManagementState.value = LoginManagementState.RequestErrorReported("Opps! Username or Password are wrong!")
                            }
                        }
                        ?: run { _registerManagementState.value = LoginManagementState.LoginUserCompleted
                            _registerManagementState.value = LoginManagementState.RequestErrorReported("Opps! Username or Password are wrong!")
                        }
                } else {
                    _registerManagementState.value = LoginManagementState.RequestErrorReported(context.getString(R.string.try_it_again))
                }
                println("Done launch")
            }
            println("Done!")
        } else {
            _registerManagementState.value = LoginManagementState.FormErrorReported(context.getString(
                R.string.sign_in_field_empty))
        }

    }

    fun onRecoverPassword(context: Context, forgotPassword: ForgotPasswordModel){
        if(isFormValid(model = forgotPassword)){

            val job = async{
                val a = LoginRepo.forgotPasswordUser(forgotPassword)
                println("Done async")
                a
            }

            launch(Dispatchers.Main) {
                val response: Response<ForgotPasswordResponse> = job.await()
                println("Done await")

                //todo deshabilitar loading
                if (response.isSuccessful) {
                    response.body().takeIf { it != null }
                        ?.let {
                            val c: ForgotPasswordResponse = response.body()!!
                            println("forgot" + c.success)
                            if(c.user_found){
                                _registerManagementState.value = LoginManagementState.ForgotPasswordUserCompleted
                                LoginManagementState.RecoverPasswordSuccessfully(msg = context.getString(R.string.recover_password_completed))
                            }else{
                                _registerManagementState.value = LoginManagementState.RequestErrorReported("Opps! Username not found!")
                            }
                        }
                        ?: run { _registerManagementState.value = LoginManagementState.ForgotPasswordUserCompleted
                            _registerManagementState.value = LoginManagementState.RequestErrorReported("Opps! Username not found!")
                        }
                } else {
                    _registerManagementState.value = LoginManagementState.RequestErrorReported(context.getString(R.string.try_it_again))
                }
                println("Done launch")
            }
            println("Done!")
        } else {
            _registerManagementState.value = LoginManagementState.FormErrorReported(context.getString(
                            R.string.recover_password_field_empty))
        }

    }





    private fun isFormValid(model: RegisterModel) =
        with(model) { name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()}
    private fun isFormValid(model: LoginModel) =
        with(model) { login.isNotEmpty()  && password.isNotEmpty()}
    private fun isFormValid(model: ForgotPasswordModel) =
        with(model) { login.isNotEmpty()}
}