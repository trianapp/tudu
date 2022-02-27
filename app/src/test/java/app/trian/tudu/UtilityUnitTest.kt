package app.trian.tudu

import app.trian.tudu.ui.component.calendar.InDateStyle
import app.trian.tudu.ui.component.calendar.MonthConfig
import app.trian.tudu.ui.component.calendar.OutDateStyle
import kotlinx.coroutines.Job
import org.junit.Test

import org.junit.Assert.*
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilityUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testDateFormat(){

    }

    @Test
    fun generateMonth(){
        val uninterruptedJob = Job()
        val monthConfig = MonthConfig(
            outDateStyle = OutDateStyle.END_OF_ROW,
            inDateStyle = InDateStyle.ALL_MONTHS,
            maxRowCount = 6,
            startMonth = YearMonth.now(),
            endMonth = YearMonth.of(2022,4),
            firstDayOfWeek = DayOfWeek.FRIDAY,
            job = uninterruptedJob,
            hasBoundaries = false
        )

        assertEquals("sas","sas")
    }
}