package app.trian.datepicker

import android.app.AlarmManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.joda.time.DateTime

/**
 *
 * author Trian Damai
 * created_at 16/02/22 - 08.57
 * site https://trian.app
 */

@Composable
fun DatePickerSlider(
    modifier: Modifier = Modifier,
    generateDaysCount:Int = 5,
    initialDate: Long =0,
    currentDate:Long = 0,
    onDateSelected:(date:DateTime,index:Int)->Unit = { _, _ ->}
) {
    val DAY_MILLIS = AlarmManager.INTERVAL_DAY
    var selected by remember {
        mutableStateOf(0)
    }

    LazyRow(content = {
        items(count = generateDaysCount){
            index: Int ->
            val actualDate = DateTime(
                initialDate+(DAY_MILLIS*index)
            )
            Day(
                selected = selected == index,
                currentDate = currentDate,
                index = index,
                actualDate = actualDate,
                onSelected = {
                    selected = index
                    onDateSelected(it,index)
                }
            )
        }
    })
}


@Composable
fun Day(
    modifier: Modifier = Modifier,
    selected:Boolean,
    currentDate: Long=0,
    index:Int,
    actualDate:DateTime,
    onSelected:(date:DateTime)->Unit={}
){

    Column(
        modifier = modifier.padding(
            vertical = 6.dp,
            horizontal = 10.dp
        )
            .clickable { onSelected(actualDate) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = actualDate.getDayName(),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = MaterialTheme.colors.primary
            )
        )

        Column(
            modifier = modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(if(selected) MaterialTheme.colors.onBackground else Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = actualDate.dayOfMonth.toString())
            Text(text = actualDate.getMonth())
        }
    }
}

@Preview
@Composable
fun PreviewDatePickerSlider(){
    MaterialTheme {
        DatePickerSlider()
    }
}