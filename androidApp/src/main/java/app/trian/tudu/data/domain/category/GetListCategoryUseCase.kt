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

class GetListCategoryUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(): Flow<Response<List<CategoryModel>>> = flow {
        emit(Response.Loading)
        val category = db
            .categoryQueries
            .getListCategory()
            .executeAsList()
            .map { it.toModel() }
            .toMutableList()

        category.add(
            0,
            CategoryModel(
                categoryId = "all",
                categoryName = "All",
                createdAt = "",
                updatedAt = ""
            )
        )

        emit(Response.Result(category))
    }.flowOn(Dispatchers.Default)
}