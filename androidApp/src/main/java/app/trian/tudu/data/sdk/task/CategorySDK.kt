package app.trian.tudu.data.sdk.task


import app.trian.tudu.data.DriverFactory
import app.trian.tudu.data.createDatabase
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.CategoryWithCount
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategorySDK(
    driverFactory: DriverFactory,
) {
    private val db = createDatabase(driverFactory)

    //region category
    @Throws(
        Exception::class
    )
    suspend fun getListCategory(): Flow<Response<List<CategoryModel>>> = flow {
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

    @Throws(
        Exception::class
    )
    suspend fun getListCategoryWithCounter(): Flow<Response<List<CategoryWithCount>>> = flow {
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

    @Throws(
        Exception::class
    )
    suspend fun getCategoryByTask(taskId: String): Flow<Response<List<CategoryModel>>> = flow {
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

    @Throws(
        Exception::class
    )
    suspend fun createNewCategory(
        categoryModel: CategoryModel,
    ): Flow<Response<CategoryModel>> = flow {
        emit(Response.Loading)
        db.categoryQueries.insertCategory(
            categoryName = categoryModel.categoryName,
            categoryId = categoryModel.categoryId,
            createdAt = categoryModel.createdAt,
            updatedAt = categoryModel.updatedAt
        )
        emit(Response.Result(categoryModel))
    }.flowOn(Dispatchers.Default)

    @Throws(
        Exception::class
    )
    suspend fun updateCategory(
        categoryModel: CategoryModel,
    ): Flow<Response<CategoryModel>> = flow {
        emit(Response.Loading)
        db.categoryQueries.updateCategory(
            categoryName = categoryModel.categoryName,
            categoryId = categoryModel.categoryId,
            updatedAt = categoryModel.updatedAt
        )
        emit(Response.Result(categoryModel))
    }.flowOn(Dispatchers.Default)

    @Throws(
        Exception::class
    )
    suspend fun deleteCategory(categoryId: String): Flow<Response<Any>> = flow {
        emit(Response.Loading)
        db.categoryQueries.deleteCategory(categoryId)
        emit(Response.Result(""))
    }
    //end region
}
