package app.trian.tudu

import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.TuduDatabase
import app.trian.tudu.data.local.dao.AttachmentDao
import app.trian.tudu.data.local.dao.CategoryDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@Config(
    application = HiltTestApplication::class,
    sdk = [29] ,
    manifest = Config.NONE
)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class DatabaseTest {

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @Inject
    @Named("db_test")
    lateinit var database:TuduDatabase

    private lateinit var taskDao:TaskDao
    private lateinit var todoDao:TodoDao
    private lateinit var attachmentDao: AttachmentDao
    private lateinit var categoryDao: CategoryDao


    @Before
    fun setup(){
        hiltRule.inject()
        taskDao = database.taskDao()
        todoDao = database.todoDao()
        attachmentDao = database.attachmentDao()
        categoryDao = database.categoryDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun `should insert new category`() = runBlocking {



    }

    @Test
    fun `should insert new task`(){


    }

    @Test
    fun `should add note to task`(){

    }

    @Test
    fun `should finish deadline`(){

    }


}