package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.TopicsRepo
import javax.inject.Singleton

@Module
class TopicsModule {

    @Singleton
    @Provides
    fun provideTopicsRepo(context: Context, retrofit: Retrofit): TopicsRepo =
        TopicsRepo.apply {
            ctx = context
            retroF = retrofit
        }
}