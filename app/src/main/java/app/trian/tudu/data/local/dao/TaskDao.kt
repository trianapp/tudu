package app.trian.tudu.data.local.dao

import androidx.room.*
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.TaskWithCategory
import app.trian.tudu.data.local.TaskWithTodoAndAttachmentAndCategory
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object Task
 * author Trian Damai
 * created_at 28/01/22 - 20.25
 * site https://trian.app
 */
@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM task ORDER BY created_at ASC")
    fun getListTaskWithTodoAndAttachment():Flow<List<TaskWithTodoAndAttachmentAndCategory>>

    @Transaction
    @Query("SELECT * FROM task WHERE taskId=:taskId")
    fun getTaskById(taskId:String):Flow<TaskWithCategory>

    @Insert
    fun insertNewTask(task:Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deteleTask(task: Task)
}