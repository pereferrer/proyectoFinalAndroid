package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state

sealed class LoginManagementState {

    object RegisterUserCompleted : LoginManagementState()
    object LoginUserCompleted : LoginManagementState()
    object ForgotPasswordUserCompleted : LoginManagementState()
    class UserRegistredSuccessfully(val msg: String) : LoginManagementState()
    class UserLoggedSuccessfully(val msg: String) : LoginManagementState()
    class RecoverPasswordSuccessfully(val msg: String) : LoginManagementState()
    class FormErrorReported(val errorMsg: String) : LoginManagementState()
}