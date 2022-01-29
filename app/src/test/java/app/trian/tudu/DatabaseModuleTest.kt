package app.trian.tudu

import android.content.Context
import androidx.room.Room
import app.trian.tudu.data.local.TuduDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleTest {
    @Provides
    @Named("db_test")
    fun provideInMemoryDb(
        @ApplicationContext appContext:Context
    ) = Room.inMemoryDatabaseBuilder(
        appContext,
        TuduDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()
}