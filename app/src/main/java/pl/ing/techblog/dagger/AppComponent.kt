package pl.ing.techblog.dagger

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.ing.techblog.MyApplication
import pl.ing.techblog.data.AppRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        CoreFragmentModule::class,
        ViewModelModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {
    fun repo(): AppRepository

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>() {
        abstract override fun build(): AppComponent
    }
}