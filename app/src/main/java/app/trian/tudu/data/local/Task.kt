package app.trian.tudu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    var name:String,
    @ColumnInfo(name = "deadline")
    var deadline:Long,
    @ColumnInfo(name = "done")
    var done:Boolean,
    @ColumnInfo(name = "done_at")
    var done_at:Long,
    @ColumnInfo(name = "note")
    var note:String,
    @ColumnInfo(name = "taskCategoryId")
    var category_id:String,
    @ColumnInfo(name = "created_at")
    var created_at:Long,
    @ColumnInfo(name = "updated_at")
    var updated_at:Long
)
