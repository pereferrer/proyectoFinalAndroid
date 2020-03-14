package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*

interface LoginRepository {
    suspend fun loginUser(loginModel: LoginModel): Response<LoginModelResponse>
    suspend fun forgotPasswordUser(forgotPasswordModel: ForgotPasswordModel): Response<ForgotPasswordResponse>
    suspend fun detailUser(username: String): Response<DetailUserResponse>
    suspend fun privateMessageListUser(username: String): Response<PrivateMessageListResponse>
}