package app.trian.tudu.data.domain.category

import android.content.SharedPreferences
import app.trian.tudu.data.model.listCategoriesEnglish
import app.trian.tudu.data.model.listCategoriesIn
import app.trian.tudu.data.model.toEntity
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import app.trian.tudu.table.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale
import javax.inject.Inject

class InsertCategoryOnFirstRunUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val db: Database
) {
    companion object {
        const val firstRun = "firstRun"
    }

    operator fun invoke(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        if (sharedPreferences.getBoolean(firstRun, true)) {
            val local = Locale.getDefault().language
            when {
                local.equals(Locale("en").language) ->
                    insert(listCategoriesEnglish.map { it.toEntity() })

                else ->
                    insert(listCategoriesIn.map { it.toEntity() })
            }

        }
    }.flowOn(Dispatchers.IO)

    private fun insert(categories: List<Category>) {
        db.transaction {
            categories.forEach { category ->
                db.categoryQueries.insertCategory(
                    categoryId = category.categoryId,
                    categoryName = category.categoryName,
                    createdAt = category.createdAt,
                    updatedAt = category.updatedAt
                )
            }
        }
        editor.putBoolean(firstRun, false)
        editor.apply()
    }
}