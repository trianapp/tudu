package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: Database
) {
    operator fun invoke(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            auth.signOut()
            db.taskQueries.clearTask()
            db.taskCategoryQueries.clearTaskCategory()
            db.todoQueries.clearTodo()
            db.categoryQueries.clearCategory()
            emit(Response.Result(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message.orEmpty()))
        }

    }.flowOn(Dispatchers.IO)
}