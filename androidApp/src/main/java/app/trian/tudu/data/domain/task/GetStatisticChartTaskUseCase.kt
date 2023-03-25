package app.trian.tudu.data.domain.task

import app.trian.tudu.base.extensions.getFirstDays
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
            val startWeek = from
            var currentDate = startWeek
            var currentMaxAxis = 0f

            val totalDays = listOf(6, 5, 4, 3, 2, 1, 0).reversed()

            var entries = listOf<BarEntry>()
            var labels = listOf<String>()
            totalDays.forEachIndexed { _, i ->
                val dataCount =
                    db.taskQueries
                        .getListByDueDate(currentDate.toString(), currentDate.toString())
                        .executeAsList()
                        .filter { it.taskDone?.toInt() == 1 }
                        .size
                if ((currentMaxAxis + 3f) < dataCount) {
                    currentMaxAxis = (dataCount + 3f)
                }

                entries = entries + BarEntry(
                    i.toFloat(),
                    dataCount.toFloat()
                )
                labels = labels + currentDate.format(DateTimeFormatter.ofPattern("dd/MM"))
                currentDate = currentDate.plusDays(1)
            }
            ChartModelData(
                items = entries,
                labels = labels,
                from = from,
                to = from.plusDays(6),
                max = currentMaxAxis,
                min = 0f
            )
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.IO)
}