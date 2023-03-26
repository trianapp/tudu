package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.CountTask
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetStatisticCountTaskUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke():Flow<Response<CountTask>> = flow {
        emit(Response.Loading)
        val allTask = db.taskQueries.getListTask().executeAsList().map { it.toModel() }
        val allTaskCount = allTask.size
        val completed = allTask.filter { it.taskDone }.size
        val pending = allTask.filter { !it.taskDone }.size

        emit(
            Response.Result(
                CountTask(
                    totalTask = allTaskCount,
                    completedTask = completed,
                    pendingTask = pending
                )
            )
        )
    }.flowOn(Dispatchers.IO)
}