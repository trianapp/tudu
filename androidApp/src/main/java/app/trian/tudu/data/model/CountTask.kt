package app.trian.tudu.data.model

import com.github.mikephil.charting.data.BarEntry
import com.google.errorprone.annotations.Keep
import java.time.LocalDate

@Keep
data class CountTask(
    val totalTask: Int = 0,
    val completedTask: Int = 0,
    val pendingTask: Int = 0
)

data class ChartModelData(
    val items:List<BarEntry> = listOf(),
    val labels:List<String> = listOf(),
    val from:LocalDate= LocalDate.now(),
    val to:LocalDate= LocalDate.now(),
    val max:Float=5f,
    val min:Float=0f,
)
