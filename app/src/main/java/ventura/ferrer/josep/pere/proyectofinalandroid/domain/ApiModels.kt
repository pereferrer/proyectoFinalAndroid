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

data class LoginModel(
    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("password")
    val password: String
) {
    override fun toString(): String {
        return "RegisterModel(login='$login', password='$password')"
    }
}

data class LoginModelResponse(
    @Expose
    @SerializedName("user")
    val user: User
) {
    override fun toString(): String {
        return "RegisterModelResponse(user='$user')"
    }
}

data class User(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("username")
    val username: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("avatarTemplate")
    val avatarTemplate: String,
    @Expose
    @SerializedName("email")
    val email: String
) {
    override fun toString(): String {
        return "RegisterModelResponse(id='$id',username='$username', name='$name', avatarTemplate='$avatarTemplate', email='$email')"
    }
}


data class ForgotPasswordModel(
    @Expose
    @SerializedName("login")
    val login: String
) {
    override fun toString(): String {
        return "RegisterModel(login='$login')"
    }
}

data class ForgotPasswordResponse(
    @Expose
    @SerializedName("success")
    val success: String,
    @Expose
    @SerializedName("user_found")
    val user_found: Boolean
) {
    override fun toString(): String {
        return "RegisterModelResponse(success='$success', user_found='$user_found')"
    }
}
