package pl.ing.techblog

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.splitcompat.SplitCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var featureModuleProxy: FeatureModuleProxy

    override fun attachBaseContext(ctx: Context?) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    private fun getActivity() : Activity {
        return this
    }

    private fun onModuleDownloaded() {
//        val packageName = "pl.ing.techblog.feature"
//        val className = ".FeatureActivity"
//        val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName,packageName + className)
//        startActivity(intent)

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
}
