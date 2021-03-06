package app.trian.tudu

import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.data.local.TuduDatabase
import app.trian.tudu.ui.theme.HexToJetpackColor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.time.OffsetDateTime
import javax.inject.Inject
import javax.inject.Named




@HiltAndroidTest
@Config(
    application = HiltTestApplication::class,
    manifest = Config.NONE
)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)  //
@ExperimentalCoroutinesApi
class DatabaseTest {



    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // 5

    @Inject
    @Named("db_test")
    lateinit var tuduDatabase: TuduDatabase

    @Before
    fun setUp() {
        hiltAndroidRule.inject() // 6
    }

    @After
    fun tearDown(){
        tuduDatabase.close()
    }

    @Test
    fun `should insert new task`() = runBlocking{ // 7
        val task = Task(
            taskId = "AzAka",
            category_id = "",
            name = "",
            done = false,
            deadline= OffsetDateTime.now(),
            done_at = OffsetDateTime.now(),
            note="",
            reminder=false,
            color = HexToJetpackColor.SecondBlue,
            secondColor = HexToJetpackColor.SecondRed,
            created_at = OffsetDateTime.now(),
            updated_at = OffsetDateTime.now()
        )

        val taskDao = tuduDatabase.taskDao()
        taskDao.insertNewTask(task)

        val taskInserted = taskDao.getTaskById("AzAka")

        assertEquals(task,taskInserted)
    }

    @Test
    fun `should insert category`()= runBlocking{
        val category = Category(
            categoryId = "Azka",
            name = "Wishlist",
            color = HexToJetpackColor.Blue,
            usedCount = 0,
            created_at = OffsetDateTime.now(),
            updated_at = OffsetDateTime.now()
        )

        val categoryDao = tuduDatabase.categoryDao()
        categoryDao.insertNewCategory(category)

        val insertedCategory = categoryDao
            .getListCategory()
            .take(1)
            .toList()
            .last()

        assertEquals(listOf(category),insertedCategory)
    }

    @Test
    fun `should insert todo`()= runBlocking{
        val todo = Todo(
            id = 0,
            name = "ini name",
            done = false,
            task_id = "aXkAs",
            created_at = OffsetDateTime.now(),
            updated_at = OffsetDateTime.now(),

        )

        val todoDao = tuduDatabase.todoDao()
        todoDao.insertTodoTask(todo)

        val insertedTodo = todoDao.getListCompleteTodoByTask("aXkAs",false)
            .take(1)
            .toList()
            .first()

        assertEquals(listOf(todo),insertedTodo)
    }

}