package ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.state

import ventura.ferrer.josep.pere.proyectofinalandroid.domain.DetailUserResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic

sealed class LoginManagementState {

    object RegisterUserCompleted : LoginManagementState()
    object LoginUserCompleted : LoginManagementState()
    object ForgotPasswordUserCompleted : LoginManagementState()
    object DetailUserCompleted : LoginManagementState()
    object PrivateMessageListUserCompleted : LoginManagementState()
    class UserRegistredSuccessfully(val msg: String) : LoginManagementState()
    class UserLoggedSuccessfully(val msg: String) : LoginManagementState()
    class RecoverPasswordSuccessfully(val msg: String) : LoginManagementState()
    class DetailUserSuccessfully(val detailUser: DetailUserResponse) : LoginManagementState()
    class PrivateMessageListSuccessfully(val topics: List<Topic>) : LoginManagementState()
    class FormErrorReported(val errorMsg: String) : LoginManagementState()
    class RequestErrorReported(val errorMsg: String) : LoginManagementState()
}