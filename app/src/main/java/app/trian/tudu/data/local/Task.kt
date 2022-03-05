package app.trian.tudu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

/**
 * Entity Task
 * author Trian Damai
 * created_at 28/01/22 - 10.28
 * site https://trian.app
 */

@Entity(tableName = "tb_task")
data class Task(
    @PrimaryKey
    @ColumnInfo(name = "taskId")
    var taskId:String="dummy",
    @ColumnInfo(name = "uid")
    var uid:String="dummy",
    @ColumnInfo(name = "name")
    var name:String="",
    @ColumnInfo(name = "deadline")
    var deadline:OffsetDateTime?=null,
    @ColumnInfo(name = "done")
    var done:Boolean=false,
    @ColumnInfo(name = "done_at")
    var done_at:OffsetDateTime?=null,
    @ColumnInfo(name = "note")
    var note:String="",
    @ColumnInfo(name="color")
    var color:String="",
    @ColumnInfo(name="second_color")
    var secondColor:String="",
    @ColumnInfo(name = "reminder")
    var reminder:Boolean=false,
    @ColumnInfo(name = "taskCategoryId")
    var category_id:String="",
    @ColumnInfo(name = "created_at")
    var created_at:OffsetDateTime?=null,
    @ColumnInfo(name = "updated_at")
    var updated_at:OffsetDateTime?=null
)
