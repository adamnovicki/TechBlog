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
import pl.ing.techblog.dynamic.BaseModuleInjector
import pl.ing.techblog.dynamic.FeatureModule
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

    private val injectedModules = mutableSetOf<FeatureModule>()

    private val moduleActivityInjectors = mutableListOf<DispatchingAndroidInjector<Activity>>()
    private val moduleFragmentInjectors = mutableListOf<DispatchingAndroidInjector<Fragment>>()

    private val activityInjector = AndroidInjector<Activity> { instance ->

        if (dispatchingActivityInjector.maybeInject(instance)) {
            return@AndroidInjector
        }
        moduleActivityInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }

    private val fragmentInjector = AndroidInjector<Fragment> { instance ->

        if (dispatchingFragmentInjector.maybeInject(instance)) {
            return@AndroidInjector
        }

        moduleFragmentInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }







    override fun attachBaseContext(ctx: Context) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent.inject(this)
    }




    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }


    fun addModuleInjector(module: FeatureModule) {
        if (injectedModules.contains(module)) {
            return
        }

        val clazz = Class.forName(module.injectorName)
        val moduleInjector = clazz.newInstance() as BaseModuleInjector
        moduleInjector.inject(this)

        moduleActivityInjectors.add(moduleInjector.activityInjector())
        moduleFragmentInjectors.add(moduleInjector.fragmentInjector())

        injectedModules.add(module)
    }
}