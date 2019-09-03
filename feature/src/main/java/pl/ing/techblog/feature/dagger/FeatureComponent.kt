package pl.ing.techblog.feature.dagger

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.ing.techblog.dagger.AppComponent
import pl.ing.techblog.dagger.ModuleScope
import pl.ing.techblog.dagger.ViewModelModule

/**
 * Created by adamnowicki on 2019-09-03.
 */
@ModuleScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        FeatureFragmentModule::class,
        ViewModelModule::class
    ]
)
interface FeatureComponent {
    fun inject(injector: FeatureInjector)
}