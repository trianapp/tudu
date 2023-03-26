package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.TaskWithCategory
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListTaskByDateUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(from:String,to:String):Flow<Response<List<TaskWithCategory>>> = flow {
        emit(Response.Loading)
        val result = db.transactionWithResult {
            val tasks = db.taskQueries
                .getListByDueDate(
                    from,
                    to
                )
                .executeAsList()
                .map { it.toModel() }

            val categories = db.categoryQueries
                .getListCategory()
                .executeAsList()
                .map { it.toModel() }

            val getListCategoryTask = db.taskCategoryQueries
                .getAllTaskCategory()
                .executeAsList()
                .map { it.toModel() }

            tasks.map { taskModel ->
                val findGroup = getListCategoryTask
                    .filter { group -> group.taskId == taskModel.taskId }
                    .map { group ->
                        categories
                            .filter { category -> category.categoryId == group.categoryId }
                    }
                    .flatten()

                TaskWithCategory(
                    taskModel,
                    findGroup
                )
            }
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.IO)
}