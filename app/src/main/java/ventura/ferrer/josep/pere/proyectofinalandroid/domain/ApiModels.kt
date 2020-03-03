package ventura.ferrer.josep.pere.proyectofinalandroid.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("password")
    val password: String,
    @Expose
    @SerializedName("username")
    val username: String

) {
    override fun toString(): String {
        return "RegisterModel(name='$name', email='$email', password='$password', username='$username')"
    }
}

data class RegisterModelResponse(
    @Expose
    @SerializedName("success")
    val success: Boolean,
    @Expose
    @SerializedName("active")
    val active: Boolean,
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("user_id")
    val user_id: Int
) {
    override fun toString(): String {
        return "RegisterModelResponse(success='$success', active='$active', message='$message', user_id='$user_id')"
    }
}