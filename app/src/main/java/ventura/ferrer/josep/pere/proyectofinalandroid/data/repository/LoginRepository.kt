package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModelResponse

interface LoginRepository {
    suspend fun loginUser(loginModel: LoginModel): Response<LoginModelResponse>
}