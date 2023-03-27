package app.trian.tudu.data.model

import category.Category
import java.time.LocalDateTime
import java.util.UUID

data class CategoryModel(
    val categoryId: String = "",
    val categoryName: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

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
        categoryId = UUID.randomUUID().toString(),
        categoryName = "Work",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = UUID.randomUUID().toString(),
        categoryName = "Meeting",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = UUID.randomUUID().toString(),
        categoryName = "Ideas",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    )
)

val listCategoriesIn = listOf(
    CategoryModel(
        categoryId = UUID.randomUUID().toString(),
        categoryName = "Work",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = UUID.randomUUID().toString(),
        categoryName = "Meeting",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    ),
    CategoryModel(
        categoryId = UUID.randomUUID().toString(),
        categoryName = "Ideas",
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString()
    )
)