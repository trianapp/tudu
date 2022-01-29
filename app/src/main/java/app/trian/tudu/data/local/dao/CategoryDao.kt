package app.trian.tudu.data.local.dao

import androidx.room.*
import app.trian.tudu.data.local.Category
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object
 * author Trian Damai
 * created_at 28/01/22 - 20.27
 * site https://trian.app
 */
@SuppressWarnings(
    RoomWarnings.CURSOR_MISMATCH
)
@Dao
interface CategoryDao {

    @Query("SELECT * FROM tb_category")
    fun getListCategory():List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCategory(category: Category):Long


    @Update
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)


}