package app.trian.tudu.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithTodoAndAttachmentAndCategory(
    @Embedded val task:Task,
    @Relation(
        parentColumn = "taskCategoryId",
        entityColumn = "categoryId"
    )
    val category: Category,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "todoTaskId"
    )
    val listTodo:List<Todo>,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "attachmentTaskId"
    )
    val listAttachment:List<Attachment>
)