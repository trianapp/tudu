package app.trian.tudu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.trian.tudu.data.local.dao.*

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
        Todo::class,
        AppSetting::class
    ],
    version = 14,
    exportSchema = true
)
abstract class TuduDatabase :RoomDatabase(){
    abstract fun taskDao():TaskDao
    abstract fun todoDao():TodoDao
    abstract fun attachmentDao(): AttachmentDao
    abstract fun categoryDao():CategoryDao
    abstract fun appSettingDao():AppSettingDao
    companion object{
        const val DB_NAME = "TUDU_APP"
    }
}