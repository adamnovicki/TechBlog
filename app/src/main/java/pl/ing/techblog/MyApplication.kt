package pl.ing.techblog

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

/**
 * Created by adamnowicki on 2019-08-23.
 */
class MyApplication : Application() {
    override fun attachBaseContext(ctx: Context) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }
}