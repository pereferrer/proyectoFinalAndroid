package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepo
import javax.inject.Singleton

class RegisterModule {
    @Singleton
    @Provides
    fun provideTopicsRepo(context: Context, retrofit: Retrofit): RegisterRepo =
        RegisterRepo.apply {
            ctx = context
            retroF = retrofit
        }
}