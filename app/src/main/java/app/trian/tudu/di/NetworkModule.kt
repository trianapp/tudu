package app.trian.tudu.di

import app.trian.tudu.common.DefaultDispatcherProvider
import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.data.local.dao.*
import app.trian.tudu.data.repository.TaskRepositoryImpl
import app.trian.tudu.data.repository.UserRepositoryImpl
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.data.repository.design.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module dependency injection for firebase
 * author Trian Damai
 * created_at 28/01/22 - 10.30
 * site https://trian.app
 */
@Module
@InstallIn(value = [SingletonComponent::class])
object NetworkModule {
    @Provides
    fun provideDispatcher():DispatcherProvider= DefaultDispatcherProvider()

    @Provides
    fun provideFirebaseMessaging():FirebaseMessaging=FirebaseMessaging.getInstance()

    @Provides
    fun provideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorage():FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    fun provideUserRepository(
        dispatcherProvider: DispatcherProvider,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        firebaseMessaging: FirebaseMessaging,
        taskDao:TaskDao,
        todoDao: TodoDao,
        appSettingDao: AppSettingDao
    ):UserRepository =UserRepositoryImpl(
            dispatcherProvider,
            firebaseAuth,
            firestore ,
            firebaseMessaging,
            taskDao,
            todoDao,
            appSettingDao
        )

    @Provides
    fun provideTaskRepository(
        dispatcherProvider: DispatcherProvider,
        taskDao:TaskDao,
        todoDao: TodoDao,
        categoryDao: CategoryDao,
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ):TaskRepository =TaskRepositoryImpl(
        dispatcherProvider,
        taskDao,
        todoDao,
        categoryDao,
        firestore,
        firebaseAuth
    )

}