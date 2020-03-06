package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModelResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state.LoginManagementState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(private val registerRepo: RegisterRepo):ViewModel(), CoroutineScope {

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
                            println("register" + c.message)
                            _registerManagementState.value = LoginManagementState.RegisterUserCompleted
                            LoginManagementState.UserRegistredSuccessfully(msg = context.getString(R.string.user_created))
                        }
                        ?: run { _registerManagementState.value = LoginManagementState.RegisterUserCompleted
                            println("Error 1")
                            //Todo TopicManagementState.RequestErrorReported(requestError = it)
                        }
                } else {
                    println("Error 2")
                    println("Error 2" + response.code().toString())
                    println("Error 2" + response.toString().toString())
                    println("Error 2" + response.errorBody().toString())
                    //Todo TopicManagementState.RequestErrorReported(requestError = it)
                }
                println("Done launch")
            }
            println("Done!")
        } else {
            //Todo show error
            println("Error en el formulario")
        }

    }

    private fun isFormValid(model: RegisterModel) =
        with(model) { name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()}
}