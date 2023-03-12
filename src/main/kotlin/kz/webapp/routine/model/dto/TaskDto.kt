package kz.webapp.routine.model.dto


data class AddTaskDto(
    var taskId: Int = 0,
    var task: String = "",
    var comment: String = ""
)
