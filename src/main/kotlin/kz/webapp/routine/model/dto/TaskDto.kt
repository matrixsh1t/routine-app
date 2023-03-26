package kz.webapp.routine.model.dto

import java.time.LocalDateTime


data class AddTaskDto (
    var task: String = "",
    var comment: String = "",
    var performDate: LocalDateTime = LocalDateTime.now(),
)

data class UpdateTaskDto (
    var taskId: Int = 0,
    var task: String = "",
    var comment: String = "",
    var status: String = ""
)

data class PostponeTaskDto (
    var performDate: LocalDateTime
)