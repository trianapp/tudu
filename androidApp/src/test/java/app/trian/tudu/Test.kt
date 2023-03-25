package app.trian.tudu

import app.trian.tudu.base.extensions.getFirstDays
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class Test {

    @Test
    fun `get Days of week`(){

        val date = LocalDate.now()
        assertEquals("", date.getFirstDays())


    }
}