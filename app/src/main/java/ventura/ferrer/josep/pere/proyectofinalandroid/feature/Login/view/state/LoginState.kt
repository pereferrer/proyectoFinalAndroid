package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state

sealed class LoginManagementState {

    object RegisterUserCompleted : LoginManagementState()
    object LoginUserCompleted : LoginManagementState()
    class UserRegistredSuccessfully(val msg: String) : LoginManagementState()
    class UserLoggedSuccessfully(val msg: String) : LoginManagementState()
}