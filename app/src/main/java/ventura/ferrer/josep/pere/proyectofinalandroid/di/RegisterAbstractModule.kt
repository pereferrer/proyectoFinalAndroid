package ventura.ferrer.josep.pere.proyectofinalandroid.di

import dagger.Binds
import dagger.Module
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.RegisterRepository

@Module
abstract class RegisterAbstractModule {

    @Binds
    abstract fun provideRegisterRepository(registerRepo: RegisterRepo): RegisterRepository

}