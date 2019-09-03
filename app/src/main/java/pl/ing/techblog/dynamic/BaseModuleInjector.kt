package pl.ing.techblog.dynamic

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import pl.ing.techblog.MyApplication

interface BaseModuleInjector {

    fun inject(app: MyApplication)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    fun fragmentInjector(): DispatchingAndroidInjector<Fragment>
}
