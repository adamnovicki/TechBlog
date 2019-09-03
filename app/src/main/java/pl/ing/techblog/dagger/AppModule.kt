package pl.ing.techblog.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.ing.techblog.MyApplication
import pl.ing.techblog.data.AppRepository
import pl.ing.techblog.data.AppRepositoryImpl
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: MyApplication): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideAppRepository(): AppRepository {
        return AppRepositoryImpl()
    }
}