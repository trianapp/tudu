package app.trian.tudu.ui.component.chart

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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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
    onArrowClicked:(isNext:Boolean) ->Unit ={}
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
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
        Text(text = "Daily Task Complete")
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
                    }
                    axisLeft.apply {
                        setDrawGridLines(false)
                        setDrawTopYLabelEntry(false)
                    }
                    xAxis.apply {
                        setDrawLabels(true)
                        setDrawGridLines(false)
                        setDrawAxisLine(true)
                        position=XAxis.XAxisPosition.BOTTOM
                    }
                }
            },
            update = {chart->
                chart.setRadius(50)
                if(items.isNotEmpty()) {
                    chart.data = BarData(listOf(BarDataSet(items, "Tudu")))

                }
            }
        )
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

