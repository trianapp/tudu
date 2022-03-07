package app.trian.tudu


import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


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

           // if(googlePlayServiceAvailable()){
                //FirebaseCrashlytics.getInstance()
            //}

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

        }


    }

    private fun googlePlayServiceAvailable():Boolean{

        val googleService = GoogleApiAvailability.getInstance()
        val available = googleService.isGooglePlayServicesAvailable(this)

        if(available != ConnectionResult.SUCCESS){
            if(googleService.isUserResolvableError(available)){
                googleService.showErrorNotification(this,available)
            }
            return false
        }


        return true
    }
}