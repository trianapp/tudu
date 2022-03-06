package app.trian.tudu.data.local

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.dialog.DateTimeFormat
import java.time.OffsetDateTime

@Entity(tableName = "tb_app_setting")
data class AppSetting(
    @PrimaryKey
    var idSetting:String="MyAppSetting",
    var listType:Int=0,
    var lastSync:OffsetDateTime?=null,
    var dateFormat:String=DateTimeFormat.YYYYMMDD.value,
    var timeFormat:String=DateTimeFormat.TWENTY.value,
    var theme:String=ThemeData.DEFAULT.value

)
