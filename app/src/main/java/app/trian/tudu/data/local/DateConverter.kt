package app.trian.tudu.data.local

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * converter for datetime
 * */
object DateConverter{
    @SuppressLint("NewApi")
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @SuppressLint("NewApi")
    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value:String?):OffsetDateTime?{
        return value?.let{
            return formatter.parse(it,OffsetDateTime::from)
        }
    }

    @SuppressLint("NewApi")
    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date:OffsetDateTime?):String?{
        return date?.format(formatter)
    }
}