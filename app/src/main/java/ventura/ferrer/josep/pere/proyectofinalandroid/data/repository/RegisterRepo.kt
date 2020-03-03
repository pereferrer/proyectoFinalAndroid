package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import android.content.Context
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.RegisterService
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.RegisterModelResponse

object RegisterRepo: RegisterRepository {

    lateinit var ctx: Context
    lateinit var retroF: Retrofit


    override suspend fun registerNewUser(registerModel: RegisterModel): Response<RegisterModelResponse> {
        val newUser = retroF.create(RegisterService::class.java).registerNewUserWithCoroutines(registerModel)
        println("" + retroF.toString())
        return newUser
    }
}