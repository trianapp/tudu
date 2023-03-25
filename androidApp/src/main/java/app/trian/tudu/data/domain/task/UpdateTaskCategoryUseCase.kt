package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class UpdateTaskCategoryUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskId:String,
        oldCategories: List<CategoryModel>,
        newCategories: List<TaskCategoryModel>
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.transaction {
            oldCategories.forEach {
                db.taskCategoryQueries
                    .deletTaskCategoryByTaskAndCategory(
                        taskId = taskId,
                        categoryId = it.categoryId
                    )
            }
            newCategories.forEach {
                db.taskCategoryQueries.insertTaskCategory(
                    taskCategoryId = UUID.randomUUID().toString(),
                    taskId = it.taskId,
                    categoryId = it.categoryId
                )
            }
        }
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}