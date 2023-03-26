package app.trian.tudu.data.model

import category.Category

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
