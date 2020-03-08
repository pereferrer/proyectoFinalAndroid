package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ForgotPasswordModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.ForgotPasswordResponse
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModelResponse

interface LoginService {

    @POST("session")
    suspend fun loginUserWithCoroutines(@Body loginModel: LoginModel): Response<LoginModelResponse>

    @POST("session/forgot_password")
    suspend fun forgotPasswordWithCoroutines(@Body forgotPasswordModel: ForgotPasswordModel): Response<ForgotPasswordResponse>
}