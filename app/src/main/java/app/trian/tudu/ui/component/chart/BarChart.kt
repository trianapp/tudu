package app.trian.tudu.ui.component.chart

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.trian.tudu.ui.theme.TuduTheme
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowRight24


/**
 * Bar chart
 * author Trian Damai
 * created_at 15/02/22 - 21.57
 * site https://trian.app
 */
@Composable
fun BarChartView(
    modifier: Modifier=Modifier,
    title:String = "",
    items:List<BarEntry> = listOf(),
    labels:List<String> = listOf(),
    maxAxis:Float=0f,
    minAxis:Float=0f,
    onArrowClicked:(isNext:Boolean) ->Unit ={}
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val isLight = MaterialTheme.colors.isLight
    Column(
        modifier = modifier
            .width(currentWidth)
            .height(currentWidth - 30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            )

    ) {
        Text(text = "Weekly Task Complete")
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconToggleButton(
                checked = false,
                onCheckedChange = {
                    onArrowClicked(false)
                }
            ) {
                Icon(
                    imageVector = Octicons.ArrowLeft24,
                    contentDescription = ""
                )
            }
            Text(text = title)
            IconToggleButton(
                checked = false,
                onCheckedChange = {
                    onArrowClicked(true)
                }
            ) {
                Icon(
                    imageVector = Octicons.ArrowRight24,
                    contentDescription = ""
                )
            }
        }
        AndroidView(
            modifier= modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            factory = {
                RoundedBarChart(it).apply {
                    axisRight.apply {
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                        setDrawLabels(false)

                        valueFormatter = YAxisValueFormatter()
                        textColor = if(isLight) Color.DKGRAY else Color.WHITE
                    }
                    axisLeft.apply {
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                        setDrawLabels(false)

                        spaceTop = 4f
                        valueFormatter = YAxisValueFormatter()
                        textColor = if(isLight) Color.DKGRAY else Color.WHITE
                    }
                    xAxis.apply {
                        setDrawLabels(true)
                        setDrawGridLines(false)
                        setDrawAxisLine(true)
                        position=XAxis.XAxisPosition.BOTTOM
                        textColor = if(isLight) Color.DKGRAY else Color.WHITE
                    }
                    legend.apply {
                        textColor = if(isLight) Color.DKGRAY else Color.WHITE
                    }
                    description.apply {
                        textColor = if(isLight) Color.DKGRAY else Color.WHITE
                    }


                }
            },
            update = {chart->
                chart.setRadius(50)
                if(items.isNotEmpty()) {

                    chart.xAxis.valueFormatter = XAxisTimeFormatter(labels)
                    chart.data = BarData(listOf(BarDataSet(items, "Tudu"))).apply {
                        setValueTextColor(if(isLight) Color.DKGRAY else Color.WHITE)
                    }
                    chart.invalidate()
                }
            }
        )
    }

}
class XAxisTimeFormatter(data: List<String>?) : ValueFormatter() {
    private val getCurrent: MutableList<String> = ArrayList()
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return if (getCurrent.size <= value.toInt()) "" else getCurrent[value.toInt()]
    }

    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()}"
    }

    init {
        getCurrent.addAll(data!!)
    }
}

class YAxisValueFormatter():ValueFormatter(){
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return "${value.toInt()}"
    }

    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()}"
    }
}
@Preview
@Composable
fun PreviewBarChart() {
    TuduTheme {
        BarChartView(
            title = "1 - 7 Feb 2022"
        )
    }
}

