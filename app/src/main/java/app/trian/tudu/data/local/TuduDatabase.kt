package app.trian.tudu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
        Todo::class,
        AppSetting::class
    ],
    version = 15,
    exportSchema = true,
)
@TypeConverters(
    DateConverter::class
)
abstract class TuduDatabase :RoomDatabase(){
    abstract fun taskDao():TaskDao
    abstract fun todoDao():TodoDao
    abstract fun categoryDao():CategoryDao
    abstract fun appSettingDao():AppSettingDao
    companion object{
        const val DB_NAME = "TUDU_APP"
    }
}