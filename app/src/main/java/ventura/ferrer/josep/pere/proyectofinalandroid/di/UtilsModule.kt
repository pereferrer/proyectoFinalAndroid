package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ventura.ferrer.josep.pere.proyectofinalandroid.BuildConfig
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PREFERENCES_SESSION
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PREFERENCES_SESSION_USERNAME
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class UtilsModule(private val context: Context) {

    /*@Singleton
    @Provides
    fun provideLatestNewsDb(): LatestNewsDatabase = Room.databaseBuilder(
        context, LatestNewsDatabase::class.java, "latestNews-database"
    ).build()*/

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = context

    @Provides
    fun provideSharedPreferences(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRetrofit(sharedPreferences: SharedPreferences): Retrofit = Retrofit.Builder()
        .client(provideOkHttpClient(sharedPreferences))
        .baseUrl("https://${BuildConfig.DiscourseDomain}")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

fun provideOkHttpClient(sp: SharedPreferences): OkHttpClient {
    val interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val updatedRequest = original.newBuilder()
                .header("Api-Key", BuildConfig.DiscourseApiKey)
                .header("Api-Username", sp.getString(PREFERENCES_SESSION_USERNAME, "") ?: "")
                .method(original.method, original.body)
                .build()

            return chain.proceed(updatedRequest)
        }
    }
    val interceptor2 = HttpLoggingInterceptor()
    interceptor2.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(interceptor2)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

}