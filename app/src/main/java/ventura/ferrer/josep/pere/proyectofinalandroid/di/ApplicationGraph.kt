package ventura.ferrer.josep.pere.proyectofinalandroid.di

import dagger.Component
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.Login.view.ui.LoginActivity
import javax.inject.Singleton

// @Component makes Dagger create a graph of dependencies
@Singleton
@Component(modules = [RegisterModule::class, RegisterAbstractModule::class, UtilsModule::class])
interface ApplicationGraph {


    // Add here as well functions whose input argument is the entity in which Dagger can add any
    // dependency you want
    //fun inject(topicsActivity: TopicsActivity)
    fun inject(loginActivity: LoginActivity)
}