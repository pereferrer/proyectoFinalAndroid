package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import android.content.Context
import retrofit2.Response
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.LoginService
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModel
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.LoginModelResponse

object LoginRepo: LoginRepository {

    lateinit var ctx: Context
    lateinit var retroF: Retrofit


    override suspend fun loginUser(loginModel: LoginModel): Response<LoginModelResponse> {
        val user = retroF.create(LoginService::class.java).loginUserWithCoroutines(loginModel)
        return user
    }
}