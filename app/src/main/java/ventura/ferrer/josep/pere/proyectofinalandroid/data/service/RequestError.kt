package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import com.android.volley.VolleyError

data class RequestError(val volleyError: VolleyError? = null,
                        val message: String? = null,
                        val messageResId: Int? = null)
