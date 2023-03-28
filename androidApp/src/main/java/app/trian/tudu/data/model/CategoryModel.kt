package app.trian.tudu.data.model

import app.trian.tudu.table.category.Category
import com.google.errorprone.annotations.Keep
import java.time.LocalDateTime
import java.util.UUID

@Keep
data class CategoryModel(
    val categoryId: String = "",
    val categoryName: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

@Keep
data class CategoryWithCount(
    val category: CategoryModel = CategoryModel(),
    val count: Int = 0
)

fun CategoryModel.toEntity() = Category(
    categoryId = categoryId,
    categoryName = categoryName,
    createdAt = createdAt,
    updatedAt = updatedAt
)

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