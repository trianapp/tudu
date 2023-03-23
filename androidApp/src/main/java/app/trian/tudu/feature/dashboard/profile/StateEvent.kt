package app.trian.tudu.feature.dashboard.profile

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.tudu.data.model.ChartModelData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
@Immutable
data class ProfileState(
    val showDropdownMoreOption:Boolean=false,
    val selectedDate:LocalDate= LocalDate.now()

): Parcelable

@Parcelize
@Immutable
data class ProfileDataState(
    val profilePicture:String="",
    val displayName:String="",
    val email:String="",
    val totalAllTask:Int=0,
    val totalCompletedTask:Int=0,
    val totalUnCompletedTask:Int=0,
    val currentDate:LocalTime = LocalTime.now(),
    val chartData:@RawValue ChartModelData=ChartModelData()
) : Parcelable

@Immutable
sealed class ProfileEvent{
    object GetProfile:ProfileEvent()

    class GetStatistic(val isNext:Boolean):ProfileEvent()
}