package pl.ing.techblog

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import pl.ing.techblog.dagger.AppComponent
import pl.ing.techblog.dagger.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by adamnowicki on 2019-08-23.
 */
class MyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    val appComponent by lazy {
        DaggerAppComponent.builder().create(this) as AppComponent
    }

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun attachBaseContext(ctx: Context) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragmentInjector
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent.inject(this)
    }
}