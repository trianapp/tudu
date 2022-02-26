package app.trian.tudu.ui.component

import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import app.trian.tudu.R
import app.trian.tudu.ui.theme.TuduTheme
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.yearMonth
import logcat.LogPriority
import logcat.logcat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

@Composable
fun CalendarViewCompose(
    modifier: Modifier = Modifier,
    legend:Array<DayOfWeek> = emptyArray(),
    onScroll:(year:String,month:String)->Unit={_,_->},
    onSelectedDate:(date:LocalDate)->Unit={}
) {

    val firstDayOfWeek =  WeekFields.of(Locale.getDefault()).firstDayOfWeek
    val currentMonth = YearMonth.now()
    val startMonth = currentMonth.minusMonths(10)
    val endMonth = currentMonth.plusMonths(10)
    val today = LocalDate.now()
    var selectedDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now())
    }
    val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    Column(
        modifier = modifier.fillMaxWidth()
    ){
        Row(
            modifier = modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for(i in 0..6){
                Text(
                    text = legend[i].getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase(Locale.ENGLISH),
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }
        }
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            factory = {
                CalendarView(it).apply {

                    orientation = RecyclerView.HORIZONTAL
                    scrollMode = ScrollMode.PAGED
                    outDateStyle = OutDateStyle.END_OF_ROW
                    inDateStyle = InDateStyle.ALL_MONTHS
                    dayViewResource = R.layout.item_calendar_day
                    class DayViewContainer(view:View):ViewContainer(view){
                        lateinit var day: CalendarDay
                        val textView = view.findViewById<TextView>(R.id.calendarDayText)

                        init {
                            view.setOnClickListener {
                                if(day.owner == DayOwner.THIS_MONTH){
                                    selectedDate = day.date
                                    onSelectedDate(day.date)
                                    notifyCalendarChanged()
                                }


                            }
                        }
                    }


                    dayBinder = object :DayBinder<DayViewContainer>{
                        override fun bind(container: DayViewContainer, day: CalendarDay) {
                            container.day = day
                            val textView = container.textView
                            textView.text = day.date.dayOfMonth.toString()
                            if(day.owner == DayOwner.THIS_MONTH){
                                when(day.date){
                                    selectedDate->{
                                        textView.setTextColor(resources.getColor(R.color.textActive))
                                        textView.setBackgroundResource(R.drawable.bg_date_selected)
                                    }
                                    today ->{
                                        textView.setTextColor(resources.getColor(R.color.textActive))
                                        textView.setBackgroundResource(R.drawable.bg_date)
                                    }
                                    else -> {
                                        textView.setTextColor(resources.getColor(R.color.textActive))
                                        textView.background = null
                                    }
                                }

                            }else{
                                textView.setTextColor(resources.getColor(R.color.textInActive))
                                textView.background = null
                            }
                        }

                        override fun create(view: View): DayViewContainer =DayViewContainer(view)
                    }


                }
            },
            update = {
                    view->
                view.setup(startMonth ,endMonth, firstDayOfWeek)
                view.scrollToMonth(currentMonth)
                view.monthScrollListener ={
                        onScroll(
                            it.yearMonth.year.toString(),
                            monthTitleFormatter.format(it.yearMonth)
                        )
                }

            }
        )
    }
}



@Preview
@Composable
fun PreviewCalendar(){
    TuduTheme {
        CalendarViewCompose()
    }
}