package pl.ing.techblog

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import pl.ing.techblog.data.AppRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    lateinit var featureModuleProxy: FeatureModuleProxy

    @Inject
    lateinit var repo: AppRepository

    override fun attachBaseContext(ctx: Context?) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        featureModuleProxy = FeatureModuleProxy()

        btn.setOnClickListener {
            featureModuleProxy.downloadDynamicModule(
                context = this,
                moduleName = "feature",
                getActivityCallback = {getActivity()},
                callback = {onModuleDownloaded()})
        }

        button_fragment_core.setOnClickListener {
            val fragment = Class.forName("pl.ing.techblog.fragment.CoreFragment").newInstance() as Fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        textView.text = repo.getData()
    }

    private fun getActivity() : Activity {
        return this
    }

    private fun onModuleDownloaded() {
        val feature = Class.forName("pl.ing.techblog.feature.FeatureImpl").newInstance() as Feature
        textView.setText(feature.featureString)
    }

    override fun onResume() {
        super.onResume()
        featureModuleProxy.registerStateListener()
    }

    override fun onPause() {
        super.onPause()
        featureModuleProxy.unregisterStateListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VV_CONFIRMATION_REQUEST_CODE) {
            featureModuleProxy.continueDownloading()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return (application as MyApplication).supportFragmentInjector()
    }
}
