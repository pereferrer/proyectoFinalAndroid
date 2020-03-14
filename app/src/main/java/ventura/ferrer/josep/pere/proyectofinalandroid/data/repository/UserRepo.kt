package ventura.ferrer.josep.pere.proyectofinalandroid.data.repository

import android.content.Context
import retrofit2.Retrofit

const val PREFERENCES_SESSION = "session"
const val PREFERENCES_SESSION_USERNAME = "username"

object UserRepo {

    lateinit var ctx: Context


    fun isLogged(): Boolean {
        val pref = ctx.getSharedPreferences(
            PREFERENCES_SESSION, Context.MODE_PRIVATE
        )
        val user = pref.getString(PREFERENCES_SESSION_USERNAME, null)
        return user != null
    }

    fun saveSession(username: String) {
        val pref =
            ctx.applicationContext.getSharedPreferences(
                PREFERENCES_SESSION, Context.MODE_PRIVATE
            )
        pref.edit()
            .putString(PREFERENCES_SESSION_USERNAME, username)
            .apply()
    }

    fun getUsername(): String {
        val pref = ctx.getSharedPreferences(
            PREFERENCES_SESSION, Context.MODE_PRIVATE
        )

        return pref.getString(PREFERENCES_SESSION_USERNAME, "") ?: ""
    }

    fun logOut() {
        val pref = ctx.getSharedPreferences(
            PREFERENCES_SESSION, Context.MODE_PRIVATE
        )
        pref.edit()
            .remove(PREFERENCES_SESSION_USERNAME)
            .apply()
    }
}