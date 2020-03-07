package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.LoginRepo
import javax.inject.Singleton

@Module
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginRepo(context: Context, retrofit: Retrofit): LoginRepo =
        LoginRepo.apply {
            ctx = context
            retroF = retrofit
        }
}