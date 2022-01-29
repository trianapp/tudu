package app.trian.tudu.data.local.dao

import androidx.room.*
import app.trian.tudu.data.local.Attachment
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object
 * author Trian Damai
 * created_at 28/01/22 - 20.25
 * site https://trian.app
 */
@SuppressWarnings(
    RoomWarnings.CURSOR_MISMATCH
)
@Dao
interface AttachmentDao {
    @Query("SELECT * FROM tb_attachment WHERE attachmentTaskId=:taskId")
    fun getListAttachmentByTaskId(taskId:Int):List<Attachment>

    @Insert
    fun insertNewAttachment(attachment: Attachment)

    @Update
    fun updateAttachment(attachment: Attachment)

    @Delete
    fun deleteAttachment(attachment: Attachment)
}