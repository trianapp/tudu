package app.trian.tudu.data.domain.category

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(
       categoryName:String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.categoryQueries.insertCategory(
            categoryName = categoryName,
            categoryId = UUID.randomUUID().toString(),
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}