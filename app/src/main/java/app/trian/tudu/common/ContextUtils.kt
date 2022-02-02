package app.trian.tudu.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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

fun Context.showKeyboard(){
    val activity = (this as Activity)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if(view == null){
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken,1)
}

fun Context.restartActivity(){

}