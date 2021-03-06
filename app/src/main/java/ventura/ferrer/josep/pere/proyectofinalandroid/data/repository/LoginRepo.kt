package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import android.content.Context
import retrofit2.Response
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.service.LoginService
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*

object LoginRepo: LoginRepository {

    lateinit var ctx: Context
    lateinit var retroF: Retrofit


    override suspend fun loginUser(loginModel: LoginModel): Response<LoginModelResponse> {
        val user = retroF.create(LoginService::class.java).loginUserWithCoroutines(loginModel)
        return user
    }

    override suspend fun forgotPasswordUser(forgotPasswordModel: ForgotPasswordModel): Response<ForgotPasswordResponse> {
        val user = retroF.create(LoginService::class.java).forgotPasswordWithCoroutines(forgotPasswordModel)
        return user
    }

    override suspend fun detailUser(username: String): Response<DetailUserResponse> {
        val user = retroF.create(LoginService::class.java).detailUserWithCoroutines(username)
        return user
    }

    override suspend fun privateMessageListUser(username: String): Response<PrivateMessageListResponse> {
        val user = retroF.create(LoginService::class.java).privateMessageListWithCoroutines(username)
        return user
    }
}