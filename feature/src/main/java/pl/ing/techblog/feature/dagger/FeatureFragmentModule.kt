package pl.ing.techblog.feature.dagger

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.ing.techblog.dagger.ViewModelKey
import pl.ing.techblog.feature.fragment.FeatureFragment
import pl.ing.techblog.feature.fragment.FeatureViewModel

@Module
abstract class FeatureFragmentModule {

    @ContributesAndroidInjector(modules = [FeatureTwoFragmentModule::class])
    abstract fun contributeFeatureTwoFragment(): FeatureFragment

    @Module
    abstract class FeatureTwoFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(FeatureViewModel::class)
        abstract fun bindFeatureTwoViewModel(viewModel: FeatureViewModel): ViewModel
    }

}
