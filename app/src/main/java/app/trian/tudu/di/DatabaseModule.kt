package app.trian.tudu.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.trian.tudu.common.getNowMillis
import app.trian.tudu.data.local.TuduDatabase
import app.trian.tudu.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.joda.time.DateTime

/**
 * Dependency Injection for database module
 * author Trian Damai
 * created_at 28/01/22 - 20.27
 * site https://trian.app
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideDataBase(
      @ApplicationContext  appContext:Context
    ):TuduDatabase = Room.databaseBuilder(
        appContext,
        TuduDatabase::class.java,
        TuduDatabase.DB_NAME
    ).fallbackToDestructiveMigration()
        .addCallback(object:RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) = db.run {
                //after db created
//                enableWriteAheadLogging()
//                beginTransaction()
//                val currentTime = getNowMillis()
//
//                try {
//                    execSQL("""
//                            INSERT INTO
//                        tb_category(categoryId,name,color,used_count,created_at,updated_at)
//                            VALUES
//                        ('AzkIA','Work','567DF4',0,'$currentTime','$currentTime'),
//                        ('KLaIZ','Personal','567DF4',0,'$currentTime','$currentTime'),
//                        ('MaNZA','Wishlist','567DF4',0,'$currentTime','$currentTime'),
//                        ('BeLaD','Birthday','567DF4',0,'$currentTime','$currentTime'),
//                        ('XaZzA','Home Work','567DF4',0,'$currentTime','$currentTime')
//                        """.trimIndent()
//                    )
//                    setTransactionSuccessful()
//                }finally {
//                    endTransaction()
//                }
            }
        })
        .build()

    @Provides
    fun provideTaskDao(db:TuduDatabase):TaskDao =
        db.taskDao()

    @Provides
    fun provideTodoDao(db:TuduDatabase):TodoDao =
        db.todoDao()

    @Provides
    fun provideAttachmentDao(db:TuduDatabase):AttachmentDao =
        db.attachmentDao()

    @Provides
    fun provideCategoryDao(db:TuduDatabase):CategoryDao =
        db.categoryDao()

    @Provides
    fun provideAppSettingDao(db:TuduDatabase):AppSettingDao =
        db.appSettingDao()

}