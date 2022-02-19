package app.trian.tudu.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.isSystemInDarkTheme
import app.trian.tudu.R
import logcat.logcat

/**
 * Utility for context
 * author Trian Damai
 * created_at 29/01/22 - 22.08
 * site https://trian.app
 */
fun Context.hideKeyboard(){
    val activity = (this as Activity)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if(view == null){
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken,0)
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.emailTo(from:String="",to:String, subject:String){

    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$to?subject=$subject&body=$subject - ")
        putExtra(Intent.EXTRA_EMAIL,from)
    }.also { intent ->
        this.startActivity(intent)

    }
}

fun Context.gotoApp(){
    val uri: Uri = Uri.parse("market://details?id=$packageName")
    Intent(Intent.ACTION_VIEW, uri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    }.also { intent->
        this.startActivity(intent)
    }
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.

//    try {
//        startActivity(goToMarket)
//    } catch (e: ActivityNotFoundException) {
//        startActivity(Intent(Intent.ACTION_VIEW,
//            Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
//    }
}

fun Boolean.getLogo():Int{
    val isDark = this
    return if(isDark) R.drawable.logo_dark else R.drawable.logo_light
}