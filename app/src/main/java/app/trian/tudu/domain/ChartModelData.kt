package app.trian.tudu.domain

import com.github.mikephil.charting.data.BarEntry

data class ChartModelData(
    var items:List<BarEntry> = listOf(),
    var labels:List<String> = listOf(),
    var dateFrom:Long=0,
    var dateTo:Long=0,
    var max:Float =0f,
    var min:Float=0f,
)
