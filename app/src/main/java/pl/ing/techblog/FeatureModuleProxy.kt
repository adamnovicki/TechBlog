package pl.ing.techblog

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * Created by adamnowicki on 2019-08-22.
 */

const val VV_CONFIRMATION_REQUEST_CODE = 100

class FeatureModuleProxy {
    private var manager: SplitInstallManager? = null

    private var callback: (() -> Unit)? = null

    private var getActivityCallback: (() -> Activity)? = null

    private var moduleName: String? = null

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                Timber.d("Downloaded ${state.bytesDownloaded().toInt()} from ${state.totalBytesToDownload().toInt()}")
            }
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                manager?.startConfirmationDialogForResult(state, getActivityCallback?.let { it() }, VV_CONFIRMATION_REQUEST_CODE)
            }
            SplitInstallSessionStatus.INSTALLED -> {
                callback?.let { it() }
            }
            SplitInstallSessionStatus.INSTALLING -> {
            }
            SplitInstallSessionStatus.FAILED -> {
                Timber.e("FAILED")
            }
        }
    }

    fun downloadDynamicModule(context: Context, moduleName: String,
                              getActivityCallback: (() -> Activity),
                              callback: () -> (Unit)) {

        this.manager = SplitInstallManagerFactory.create(context)
        this.getActivityCallback = getActivityCallback

        this.callback = callback
        manager?.registerListener(listener)

        this.moduleName = moduleName

        if (manager?.installedModules?.contains(moduleName) == true) {
            callback()
            return
        }

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(moduleName)
            .build()

        manager?.startInstall(request)
            ?.addOnSuccessListener {
            }
            ?.addOnFailureListener {
                Toast.makeText(context,
                    "Couldn't download feature1: " + it.message,
                    Toast.LENGTH_LONG).show()
            }

    }

    fun continueDownloading() {
        val request = SplitInstallRequest
            .newBuilder()
            .addModule(this.moduleName)
            .build()
        manager?.startInstall(request)
    }

    fun registerStateListener() {
        manager?.registerListener(listener)
    }

    fun unregisterStateListener() {
        manager?.unregisterListener(listener)
    }
}