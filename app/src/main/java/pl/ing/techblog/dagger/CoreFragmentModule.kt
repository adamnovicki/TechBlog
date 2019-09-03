package pl.ing.techblog.dagger

import androidx.lifecycle.ViewModel
import pl.ing.techblog.fragment.CoreViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.ing.techblog.fragment.CoreFragment

@Module
abstract class CoreFragmentModule {

    @ContributesAndroidInjector(modules = [CoreFragmentViewModelModule::class])
    abstract fun contributeFeatureTwoFragment(): CoreFragment

    @Module
    abstract class CoreFragmentViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(CoreViewModel::class)
        abstract fun bindCoreViewModel(viewModel: CoreViewModel): ViewModel
    }

}