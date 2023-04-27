package app.trian.tudu.data.model

import app.trian.tudu.base.extensions.Empty
import app.trian.tudu.table.category.Category
import com.google.errorprone.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Keep
@Serializable
data class CategoryModel(
    @SerialName("categoryId")
    val categoryId: String = String.Empty,
    @SerialName("categoryName")
    val categoryName: String = String.Empty,
    @SerialName("createdAt")
    val createdAt: String = String.Empty,
    @SerialName("updatedAt")
    val updatedAt: String = String.Empty
)

@Keep
@Serializable
data class CategoryWithCount(
    @SerialName("category")
    val category: CategoryModel = CategoryModel(),
    @SerialName("count")
    val count: Int = 0
)

@Keep
fun CategoryModel.toEntity() = Category(
    categoryId = categoryId,
    categoryName = categoryName,
    createdAt = createdAt,
    updatedAt = updatedAt
)

@Keep
fun Category.toModel() = CategoryModel(
    categoryId = categoryId,
    categoryName = categoryName,
    createdAt = createdAt,
    updatedAt = updatedAt
)


val listCategoriesEnglish = listOf(
    CategoryModel(
        categoryId = "da8bb576-6966-4fb1-88d7-11f0258389ab",
        categoryName = "Work",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = "da8bb576-6966-4fb1-88d7-11f0258389ac",
        categoryName = "Meeting",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = "da8bb576-6966-4fb1-88d7-11f0258389ad",
        categoryName = "Ideas",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    )
)

val listCategoriesIn = listOf(
    CategoryModel(
        categoryId ="da8bb576-6966-4fb1-88d7-11f0258389ab",
        categoryName = "Work",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId =  "da8bb576-6966-4fb1-88d7-11f0258389ac",
        categoryName = "Meeting",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = "da8bb576-6966-4fb1-88d7-11f0258389ad",
        categoryName = "Ideas",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    )
)