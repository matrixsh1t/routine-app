package kz.webapp.routine.model.dto


data class AddTaskDto (
    var task: String = "",
    var comment: String = ""
)

data class UpdateTaskDto (
    var taskId: Int = 0,
    var task: String = "",
    var comment: String = ""
)