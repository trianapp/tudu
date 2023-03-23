package app.trian.tudu.feature.splash

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SplashUiState(
    var params:String=""
) : Parcelable

sealed class SplashEvent{

    object CheckSession: SplashEvent()
}