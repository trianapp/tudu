package app.trian.tudu.data.domain.category

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(
        categoryId: String
    ): Flow<Response<Any>> = flow {
        emit(Response.Loading)
        db.categoryQueries.deleteCategory(categoryId)
        emit(Response.Result(""))
    }.flowOn(Dispatchers.IO)
}