package app.trian.tudu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity Todo
 * author Trian Damai
 * created_at 28/01/22 - 10.28
 * site https://trian.app
 */

@Entity
data class Todo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:Long,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "done")
    var done:Boolean,
    @ColumnInfo(name = "todoTaskId")
    var task_id:Long,
    @ColumnInfo(name = "created_at")
    var created_at:Long,
    @ColumnInfo(name = "updated_at")
    var updated_at:Long
)
