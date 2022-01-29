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
@Dao
interface CategoryDao {

    @Query("SELECT * FROM Category")
    fun getListCategory():Flow<List<Category>>

    @Insert
    fun insertNewCategory(category: Category)


    @Update
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)


}