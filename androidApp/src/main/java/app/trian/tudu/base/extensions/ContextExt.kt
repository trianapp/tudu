package app.trian.tudu.base.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Utility for context
 * author Trian Damai
 * created_at 29/01/22 - 22.08
 * site https://trian.app
 */
fun Context.hideKeyboard() {
    val activity = (this as Activity)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.emailTo(from: String = "", to: String, subject: String) {

    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$to?subject=$subject&body=$subject - ")
        putExtra(Intent.EXTRA_EMAIL, from)
    }.also { intent ->
        this.startActivity(intent)

    }
}

fun Context.gotoApp() {
    try {
        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
        }.also {
            this.startActivity(it)
        }
    } catch (e: ActivityNotFoundException) {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")).also {
            this.startActivity(it)
        }

    }
}
