package app.trian.tudu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

/**
 * Entity Todo
 * author Trian Damai
 * created_at 28/01/22 - 10.28
 * site https://trian.app
 */

@Entity(tableName = "tb_todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long?=null,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "done")
    var done:Boolean,
    @ColumnInfo(name = "todoTaskId")
    var task_id:String,
    @ColumnInfo(name = "created_at")
    var created_at:OffsetDateTime?=null,
    @ColumnInfo(name = "updated_at")
    var updated_at:OffsetDateTime?=null
)
