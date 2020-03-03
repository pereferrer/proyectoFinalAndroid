package ventura.ferrer.josep.pere.proyectofinalandroid.di

import dagger.Binds
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepository

abstract class RegisterAbstractModule {

    @Binds
    abstract fun provideRegisterRepository(registerRepo: RegisterRepo): RegisterRepository
}