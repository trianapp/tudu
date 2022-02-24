package app.trian.tudu


import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import logcat.logcat
import app.trian.tudu.R


/**
 * main application
 * author Trian Damai
 * created_at 04/02/22 - 17.25
 * site https://trian.app
 */
@HiltAndroidApp
class MainApplication:MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
        try {
            FirebaseApp.initializeApp(this)
            FirebaseCrashlytics.getInstance()

            //configure toast
            val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.poppins_regular)
            Toasty.Config.getInstance()
                .tintIcon(true)
                .setToastTypeface(typeface!!)
                .setTextSize(16)
                .allowQueue(true)
                .setGravity(1,0,1)
                .supportDarkTheme(true)
                .apply()


        }catch (e:Exception){
            Firebase.crashlytics.recordException(e)
            logcat { e.message.toString() }
        }


    }
}