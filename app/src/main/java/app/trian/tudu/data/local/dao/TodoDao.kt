package app.trian.tudu.data.local.dao

import androidx.room.*
import app.trian.tudu.data.local.Todo
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object
 * author Trian Damai
 * created_at 28/01/22 - 20.27
 * site https://trian.app
 */
@Dao
interface TodoDao {

    @Query("SELECT * FROM todo WHERE todoTaskId =:taskId")
    fun getListTodoByTask(taskId:String):Flow<List<Todo>>

    @Insert
    fun insertTodoTask(todo: Todo)

    @Update
    fun updateTodoTask(todo: Todo)

    @Delete
    fun deleteTodoTask(todo: Todo)
}