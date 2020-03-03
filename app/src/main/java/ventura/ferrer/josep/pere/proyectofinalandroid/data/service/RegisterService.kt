package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.*
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModelResponse

interface RegisterService {

    @POST("users")
    suspend fun registerNewUserWithCoroutines(@Body registerModel: RegisterModel): Response<RegisterModelResponse>

}