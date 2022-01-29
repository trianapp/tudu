package app.trian.tudu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.trian.tudu.data.local.dao.AttachmentDao
import app.trian.tudu.data.local.dao.CategoryDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao

/**
 * Database
 * author Trian Damai
 * created_at 28/01/22 - 10.28
 * site https://trian.app
 */
@Database(
    entities = [
        Task::class,
        Category::class,
        Attachment::class,
        Todo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TuduDatabase :RoomDatabase(){
    abstract fun taskDao():TaskDao
    abstract fun todoDao():TodoDao
    abstract fun attachmentDao(): AttachmentDao
    abstract fun categoryDao():CategoryDao
    companion object{
        const val DB_NAME = "DB_NAME"
    }
}