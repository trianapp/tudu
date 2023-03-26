package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        categoryModel: CategoryModel,
    ): Flow<Response<CategoryModel>> = flow {
        emit(Response.Loading)
        db.categoryQueries.updateCategory(
            categoryName = categoryModel.categoryName,
            categoryId = categoryModel.categoryId,
            updatedAt = LocalDate.now().toString()
        )
        emit(Response.Result(categoryModel))
    }.flowOn(Dispatchers.Default)
}