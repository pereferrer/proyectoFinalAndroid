package ventura.ferrer.josep.pere.proyectofinalandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ventura.ferrer.josep.pere.proyectofinalandroid.data.repository.TopicsRepo
import ventura.ferrer.josep.pere.proyectofinalandroid.database.EhHoDatabase
import javax.inject.Singleton

@Module
class TopicsModule {

    @Singleton
    @Provides
    fun provideTopicsRepo(context: Context, retrofit: Retrofit, ehHoDatabase: EhHoDatabase): TopicsRepo =
        TopicsRepo.apply {
            db = ehHoDatabase
            ctx = context
            retroF = retrofit
        }
}