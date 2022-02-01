package app.trian.tudu.common

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * Google sign in option
 * author Trian Damai
 * created_at 01/02/22 - 22.13
 * site https://trian.app
 */

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(
        GoogleSignInOptions.DEFAULT_SIGN_IN
    ).requestIdToken("496234949728-hdu85od2mv4gfvhbjff5ldbqkm7epcog.apps.googleusercontent.com")
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context,signInOptions)
}