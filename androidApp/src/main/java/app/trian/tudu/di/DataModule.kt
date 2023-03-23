package app.trian.tudu.di

import android.content.Context
import app.trian.tudu.data.DriverFactory
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.data.sdk.task.CategorySDK
import app.trian.tudu.data.sdk.task.TaskSDK
import app.trian.tudu.data.sdk.task.TodoSDK
import com.google.firebase.auth.FirebaseAuth
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
    fun provideDriverFactory(
        @ApplicationContext appContext: Context
    ): DriverFactory = DriverFactory(appContext)

    @Provides
    fun providerAuthSDK(
        driverFactory: DriverFactory
    ): AuthSDK = AuthSDK(
        driverFactory = driverFactory,
        firebaseAuth = FirebaseAuth.getInstance()
    )

    @Provides
    fun provideTaskSdk(
        driverFactory: DriverFactory
    ): TaskSDK = TaskSDK(
        driverFactory = driverFactory
    )

    @Provides
    fun provideTodoSdk(
        driverFactory: DriverFactory
    ): TodoSDK = TodoSDK(
        driverFactory = driverFactory
    )

    @Provides
    fun provideCategorySdk(
        driverFactory: DriverFactory
    ): CategorySDK = CategorySDK(
        driverFactory = driverFactory
    )

}