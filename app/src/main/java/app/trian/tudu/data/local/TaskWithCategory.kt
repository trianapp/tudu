package app.trian.tudu.data.local

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Get task with category
 * author Trian Damai
 * created_at 29/01/22 - 10.21
 * site https://trian.app
 */
data class TaskWithCategory(
    @Embedded val task:Task,
    @Relation(
        parentColumn = "taskCategoryId",
        entityColumn = "categoryId"
    )
    val category: Category
)