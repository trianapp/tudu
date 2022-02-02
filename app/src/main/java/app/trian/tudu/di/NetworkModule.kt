package app.trian.tudu.di

import app.trian.tudu.common.DefaultDispatcherProvider
import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.data.local.dao.AttachmentDao
import app.trian.tudu.data.local.dao.CategoryDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import app.trian.tudu.data.repository.TaskRepositoryImpl
import app.trian.tudu.data.repository.UserRepositoryImpl
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.data.repository.design.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorage():FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    fun provideUserRepository(
        dispatcherProvider: DispatcherProvider,
        firebaseAuth: FirebaseAuth,
        taskDao:TaskDao,
        todoDao: TodoDao,
    ):UserRepository =UserRepositoryImpl(
            dispatcherProvider,
            firebaseAuth,
            taskDao,
            todoDao
        )

    @Provides
    fun provideTaskRepository(
        dispatcherProvider: DispatcherProvider,
        taskDao:TaskDao,
        todoDao: TodoDao,
        attachmentDao: AttachmentDao,
        categoryDao: CategoryDao,
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ):TaskRepository =TaskRepositoryImpl(
        dispatcherProvider,
        taskDao,
        todoDao,
        attachmentDao,
        categoryDao,
        firestore,
        firebaseAuth
    )

}