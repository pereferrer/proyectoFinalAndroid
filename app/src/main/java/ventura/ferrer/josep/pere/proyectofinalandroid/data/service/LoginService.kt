package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModelResponse

interface LoginService {

    @POST("session")
    suspend fun loginUserWithCoroutines(@Body loginModel: LoginModel): Response<LoginModelResponse>

}