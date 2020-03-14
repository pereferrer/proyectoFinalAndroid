package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.*

interface LoginService {

    @POST("session")
    suspend fun loginUserWithCoroutines(@Body loginModel: LoginModel): Response<LoginModelResponse>

    @POST("session/forgot_password")
    suspend fun forgotPasswordWithCoroutines(@Body forgotPasswordModel: ForgotPasswordModel): Response<ForgotPasswordResponse>

    @GET("/users/{username}.json")
    suspend fun detailUserWithCoroutines(@Path("username") username: String): Response<DetailUserResponse>

    @GET("/topics/private-messages/{username}.json")
    suspend fun privateMessageListWithCoroutines(@Path("username") username: String): Response<PrivateMessageListResponse>

}