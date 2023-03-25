package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.ChartModelData
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetStatisticChartTaskUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(from:LocalDate):Flow<Response<ChartModelData>> = flow {
        emit(Response.Loading)
        val result = db.transactionWithResult {
            val startWeek = from.minusDays(7)
            var nextDay = from.plusDays(1)
            var lastDay = nextDay.minusDays(1)
            var currentMaxAxis = 0f

            val totalDays = listOf(6, 5, 4, 3, 2, 1, 0).reversed()
            var entries = listOf<BarEntry>()
            var labels = listOf<String>()
            totalDays.forEachIndexed { _, i ->
                val dataCount =
                    db.taskQueries
                        .getListByDate(nextDay.toString(), lastDay.toString())
                        .executeAsList()
                        .filter { it.taskDone?.toInt() == 1 }
                        .size
                if (currentMaxAxis < dataCount) {
                    currentMaxAxis = (dataCount + 2).toFloat()
                }

                entries = entries + BarEntry(
                    i.toFloat(),
                    dataCount.toFloat()
                )
                nextDay = lastDay
                lastDay = nextDay.minusDays(1)

                labels = labels + nextDay.format(DateTimeFormatter.ofPattern("dd/MM"))
            }
            ChartModelData(
                items = entries,
                labels = labels,
                from = startWeek,
                to = from,
                max = currentMaxAxis,
                min = 0f
            )
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.IO)
}