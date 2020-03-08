package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ForgotPasswordModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ForgotPasswordResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModelResponse

interface LoginRepository {
    suspend fun loginUser(loginModel: LoginModel): Response<LoginModelResponse>
    suspend fun forgotPasswordUser(forgotPasswordModel: ForgotPasswordModel): Response<ForgotPasswordResponse>
}