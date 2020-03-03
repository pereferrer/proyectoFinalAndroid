package ventura.ferrer.josep.pere.proyectofinalandroid.data.service

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object ApiRequestQueue {

    private var requestQueue: RequestQueue? = null

    fun getRequestQueue(context: Context): RequestQueue {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context)

        return requestQueue as RequestQueue
    }
}