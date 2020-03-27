package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.PostsRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.database.EhHoDatabase
import javax.inject.Singleton

@Module
class PostsModuleModule {

    @Singleton
    @Provides
    fun provideTopicsRepo(context: Context, latestNewsDatabase: EhHoDatabase, retrofit: Retrofit): PostsRepo =
        PostsRepo.apply {
            db = latestNewsDatabase
            ctx = context
            retroF = retrofit
        }

}