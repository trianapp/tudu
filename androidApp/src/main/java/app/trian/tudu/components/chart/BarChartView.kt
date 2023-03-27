package app.trian.tudu.components.chart

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Bar chart
 * author Trian Damai
 * created_at 15/02/22 - 21.57
 * site https://trian.app
 */
@Composable
fun BarChartView(
    modifier: Modifier =Modifier,
    title:String = "",
    maxAxis:Float=5f,
    items:List<BarEntry> = listOf(),
    labels:List<String> = listOf(),
    onArrowClicked:(isNext:Boolean) ->Unit ={}
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val isDark = isSystemInDarkTheme()
    Column(
        modifier = modifier
            .width(currentWidth)
            .height(currentWidth - 30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                vertical = 16.dp
            )

    ) {
        Text(
            text = stringResource(R.string.title_chart_task_statistics),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
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
                    imageVector =Icons.Outlined.ArrowLeft,
                    contentDescription = "Previous week"
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
                    imageVector = Icons.Outlined.ArrowRight,
                    contentDescription = "Next Week"
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
                        textColor = if(isDark) Color.WHITE else Color.DKGRAY
                    }
                    axisLeft.apply {
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                        setDrawLabels(false)

                        spaceTop = 4f
                        valueFormatter = YAxisValueFormatter()
                        textColor = if(isDark) Color.WHITE else Color.DKGRAY
                    }
                    xAxis.apply {
                        axisMaximum = 7f
                        setDrawLabels(true)
                        setDrawGridLines(false)
                        setDrawAxisLine(true)
                        position= XAxis.XAxisPosition.BOTTOM
                        textColor = if(isDark) Color.WHITE else Color.DKGRAY
                    }
                    setVisibleYRangeMaximum(
                        maxAxis,
                        YAxis.AxisDependency.LEFT
                    )

                    legend.apply {
                        textColor = if(isDark) Color.WHITE else Color.DKGRAY
                    }
                    description.apply {
                        textColor = if(isDark) Color.WHITE else Color.DKGRAY
                    }

                }
            },
            update = {chart->
                chart.setRadius(30)
                if(items.isNotEmpty()) {
                    chart.xAxis.valueFormatter = XAxisTimeFormatter(labels)
                    chart.data = BarData(listOf(BarDataSet(items, "Tudu"))).apply {
                        setValueTextColor(if(isDark) Color.WHITE else Color.DKGRAY)
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
    BaseMainApp {
        BarChartView(
            title = "1 - 7 Feb 2022"
        )
    }
}