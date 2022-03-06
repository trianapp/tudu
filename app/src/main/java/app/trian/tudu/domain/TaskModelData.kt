package app.trian.tudu.domain

import app.trian.tudu.common.fromOffsetDateTime
import app.trian.tudu.common.getNowMillis
import app.trian.tudu.common.toOffsetDateTime
import app.trian.tudu.data.local.Task

data class TaskModel(
    var task:List<Task> = emptyList(),
    var updated_at: Long =0
)
data class TaskData(
    var taskId:String="dummy",
    var uid:String="dummy",
    var name:String="",
    var deadline:String="",
    var done:Boolean=false,
    var done_at:String="",
    var note:String="",
    var color:String="",
    var second_color:String="",
    var reminder:Boolean=false,
    var category_id:String="",
    var created_at:String="",
    var updated_at:String=""
)

fun TaskModel.toHashMap() = hashMapOf(
    "task" to task.map { it.toHashMap() },
    "updated_at" to getNowMillis()
)

fun Task.toHashMap() = hashMapOf(
    "taskId" to taskId,
    "uid" to uid,
    "name" to name,
    "deadline" to deadline.fromOffsetDateTime(),
    "done" to done,
    "done_at" to done_at.fromOffsetDateTime(),
    "note" to note,
    "color" to color,
    "second_color" to secondColor,
    "reminder" to reminder,
    "category_id" to category_id,
    "created_at" to created_at.fromOffsetDateTime(),
    "updated_at" to updated_at.fromOffsetDateTime(),
)

fun TaskData.toTask() = Task(
    taskId = taskId,
    uid = uid,
    name = name,
    deadline = deadline.toOffsetDateTime(),
    done = done,
    done_at = done_at.toOffsetDateTime(),
    note = note,
    color=color,
    secondColor = second_color,
    reminder = reminder,
    category_id = category_id,
    created_at = created_at.toOffsetDateTime(),
    updated_at = updated_at.toOffsetDateTime()
)
