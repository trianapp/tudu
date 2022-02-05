package app.trian.tudu

import android.app.Application
import androidx.multidex.MultiDexApplication
import app.trian.tudu.common.DefaultDispatcherProvider
import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.data.repository.design.TaskRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

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
        }catch (e:Exception){
            Firebase.crashlytics.recordException(e)
            logcat { e.message.toString() }
        }


    }
}