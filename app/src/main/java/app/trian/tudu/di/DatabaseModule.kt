package app.trian.tudu.di

import android.content.Context
import androidx.room.Room
import app.trian.tudu.data.local.TuduDatabase
import app.trian.tudu.data.local.dao.AttachmentDao
import app.trian.tudu.data.local.dao.CategoryDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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

}