package app.trian.tudu.data.local.dao

import androidx.room.*
import app.trian.tudu.data.local.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object Task
 * author Trian Damai
 * created_at 28/01/22 - 20.25
 * site https://trian.app
 */
@SuppressWarnings(
    RoomWarnings.CURSOR_MISMATCH
)
@Dao
interface TaskDao {

    @Query("SELECT * FROM tb_task ORDER BY created_at DESC")
    fun getListTask():Flow<List<Task>>

    @Query("SELECT * FROM tb_task ORDER BY created_at DESC")
    fun getListTaskNoFlow():List<Task>

    @Query("SELECT * FROM tb_task WHERE taskId=:taskId")
    fun getTaskById(taskId:String):Task?

    @Query("SELECT * FROM tb_task WHERE taskCategoryId=:categoryId ORDER BY created_at DESC")
    fun getListTaskByCategory(categoryId:String):Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewTask(task:Task)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBatchTask(tasks:List<Task>)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM tb_task")
    fun deleteAll()
}