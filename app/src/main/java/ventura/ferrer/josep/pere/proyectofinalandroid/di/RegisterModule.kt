package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepo
import javax.inject.Singleton


@Module
class RegisterModule {

    @Singleton
    @Provides
    fun provideRegisterRepo(context: Context, retrofit: Retrofit): RegisterRepo =
        RegisterRepo.apply {
            ctx = context
            retroF = retrofit
        }
}