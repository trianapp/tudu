package app.trian.tudu.data.domain.category

import app.trian.tudu.data.model.CategoryWithCount
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListCategoryWithCounterUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(): Flow<Response<List<CategoryWithCount>>> = flow {
        emit(Response.Loading)
        val result = db.transactionWithResult {
            val category = db.categoryQueries
                .getListCategory()
                .executeAsList()
                .map { it.toModel() }

            val count = db.taskCategoryQueries
                .getAllTaskCategory()
                .executeAsList()
                .map { it.toModel() }

            category
                .map { categoryModel ->
                    val filterCount = count.filter { it.categoryId == categoryModel.categoryId }.size
                    CategoryWithCount(
                        category = categoryModel,
                        count = filterCount
                    )
                }
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.Default)
}