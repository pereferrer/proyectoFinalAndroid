package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state

sealed class LoginManagementState {

    object RegisterUserCompleted : LoginManagementState()
    class UserRegistredSuccessfully(val msg: String) : LoginManagementState()
}