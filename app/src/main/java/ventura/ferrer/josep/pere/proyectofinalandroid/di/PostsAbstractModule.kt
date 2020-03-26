package ventura.ferrer.josep.pere.proyectofinalandroid.di

import dagger.Binds
import dagger.Module
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PostsRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PostsRepository

@Module
abstract class PostsAbstractModule {

    @Binds
    abstract fun providePostsRepository(postRepo: PostsRepo): PostsRepository

}