package app.trian.tudu.di

import android.content.Context
import android.content.SharedPreferences
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.trian.tudu.sqldelight.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class
)
object DataModule {

    @Provides
    fun provideSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver = AndroidSqliteDriver(
        Database.Schema,
        context,
        "tudu_trian_app.db"
    )
    @Provides
    fun provideDatabase(
        sqlDriver: SqlDriver
    ): Database = Database(sqlDriver)

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()


    @Provides
    fun provideFirebaseStorage(): FirebaseStorage =
        FirebaseStorage.getInstance()

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext appContext: Context
    ):SharedPreferences = appContext.getSharedPreferences(
        "tudu",
        Context.MODE_PRIVATE
    )

    @Provides
    fun provideSharedPrefEditor(
        sp:SharedPreferences
    ):SharedPreferences.Editor =  sp.edit()
}