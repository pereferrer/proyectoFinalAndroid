package ventura.ferrer.josep.pere.proyectofinalandroid.di

import dagger.Binds
import dagger.Module
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.LoginRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.LoginRepository
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepository

@Module
abstract class LoginAbstractModule {

    @Binds
    abstract fun provideLoginRepository(loginRepo: LoginRepo): LoginRepository

}