package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import ventura.ferrer.josep.pere.proyectofinalandroid.BuildConfig

class UserRequest(
    private val username: String,
    method: Int,
    url: String,
    body: JSONObject?,
    listener: (response: JSONObject?) -> Unit,
    errorListener: (errorResponse: VolleyError) -> Unit
) : JsonObjectRequest(method, url, body, listener, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Api-Key"] = BuildConfig.DiscourseApiKey
        headers["Api-Username"] = username

        return headers
    }
}