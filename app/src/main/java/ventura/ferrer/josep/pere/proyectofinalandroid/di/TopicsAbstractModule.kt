package ventura.ferrer.josep.pere.proyectofinalandroid.di

import dagger.Binds
import dagger.Module
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.TopicsRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.TopicsRepository

@Module
abstract class TopicsAbstractModule {

    @Binds
    abstract fun provideLoginRepository(topicsRepo: TopicsRepo): TopicsRepository

}