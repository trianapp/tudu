package app.trian.tudu.data.local

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_app_setting")
data class AppSetting(
    @PrimaryKey
    var idSetting:String="",
    var listType:Int,
    var lastSync:Long,
    var dateFormat:String,
    var timeFormat:String

)
