package app.trian.tudu.feature.auth.resetPassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ResetPasswordState(
    val email: String = "",

    val isLoading:Boolean=false
) : Parcelable

@Immutable
sealed class ResetPasswordEvent {
    object Submit : ResetPasswordEvent()
}