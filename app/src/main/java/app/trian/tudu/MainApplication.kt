package app.trian.tudu

import android.app.Application
import app.trian.tudu.common.DefaultDispatcherProvider
import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.data.repository.design.TaskRepository
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import javax.inject.Inject

@HiltAndroidApp
class MainApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
        FirebaseApp.initializeApp(this)

    }
}