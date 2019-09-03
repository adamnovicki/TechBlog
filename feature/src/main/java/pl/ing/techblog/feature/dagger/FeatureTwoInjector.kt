package pl.ing.techblog.feature.dagger

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import pl.ing.techblog.MyApplication
import pl.ing.techblog.dynamic.BaseModuleInjector
import javax.inject.Inject

class FeatureInjector: BaseModuleInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun inject(app: MyApplication) {
        DaggerFeatureComponent
            .builder()
            .appComponent(app.appComponent)
            .build()
            .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityInjector
    }

    override fun fragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return fragmentInjector
    }
}
