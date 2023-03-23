package app.trian.tudu.feature.auth.onboard

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class OnboardState(
    val isLoading: Boolean = false
) : Parcelable

@Immutable
sealed class OnboardEvent {
    object SignInWithGoogle : OnboardEvent()
}