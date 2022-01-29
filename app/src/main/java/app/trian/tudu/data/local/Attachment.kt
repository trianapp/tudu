package app.trian.tudu.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity Attachment
 * author Trian Damai
 * created_at 28/01/22 - 10.28
 * site https://trian.app
 */
@Entity
data class Attachment(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:Long,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "url")
    var url:String,
    @ColumnInfo(name = "asset")
    var asset:String,
    @ColumnInfo(name = "attachmentTaskId")
    var task_id:Long,
    @ColumnInfo(name = "created_at")
    var created_at:Long,
    @ColumnInfo(name = "updated_at")
    var updated_at:Long
)
