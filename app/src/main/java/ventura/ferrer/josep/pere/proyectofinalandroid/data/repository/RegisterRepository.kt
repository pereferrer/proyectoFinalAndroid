package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import retrofit2.Response
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModelResponse

interface RegisterRepository {

    suspend fun registerNewUser(registerModel: RegisterModel): Response<RegisterModelResponse>
}