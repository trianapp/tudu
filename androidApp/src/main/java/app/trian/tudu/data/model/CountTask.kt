package app.trian.tudu.data.model

import com.github.mikephil.charting.data.BarEntry
import com.google.errorprone.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Keep
@Serializable
data class CountTask(
    @SerialName("totalTask")
    val totalTask: Int = 0,
    @SerialName("completedTask")
    val completedTask: Int = 0,
    @SerialName("pendingTask")
    val pendingTask: Int = 0
)

@Keep
data class ChartModelData(
    @SerialName("items")
    val items:List<BarEntry> = listOf(),
    @SerialName("labels")
    val labels:List<String> = listOf(),
    @SerialName("from")
    val from:LocalDate= LocalDate.now(),
    @SerialName("to")
    val to:LocalDate= LocalDate.now(),
    @SerialName("max")
    val max:Float=5f,
    @SerialName("min")
    val min:Float=0f,
)
