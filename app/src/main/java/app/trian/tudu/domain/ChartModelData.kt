package app.trian.tudu.domain

import com.github.mikephil.charting.data.BarEntry
import java.time.OffsetDateTime

data class ChartModelData(
    var items:List<BarEntry> = listOf(),
    var labels:List<String> = listOf(),
    var dateFrom:OffsetDateTime?=null,
    var dateTo:OffsetDateTime?=null,
    var max:Float =0f,
    var min:Float=0f,
)
