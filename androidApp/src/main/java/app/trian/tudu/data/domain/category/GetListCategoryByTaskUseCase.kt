package app.trian.tudu.data.domain.category

import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListCategoryByTaskUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskId:String
    ): Flow<Response<List<CategoryModel>>> = flow {
        emit(Response.Loading)

        val result = db.transactionWithResult {
            val getListCategoryTask = db.taskCategoryQueries
                .getAllTaskCategoryByTaskId(taskId)
                .executeAsList()
                .map { it.toModel() }

            val categories = db.categoryQueries
                .getListCategory()
                .executeAsList()
                .map { it.toModel() }

            getListCategoryTask
                .filter { group -> group.taskId == taskId }
                .map { group ->
                    categories
                        .filter { category -> category.categoryId == group.categoryId }
                }
                .flatten()
        }

        emit(Response.Result(result))
    }.flowOn(Dispatchers.Default)
}