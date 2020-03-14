package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.LoginRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.UserRepo
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

    @Singleton
    @Provides
    fun provideUserRepo(context: Context): UserRepo {
        UserRepo.ctx = context
        return UserRepo
    }
}