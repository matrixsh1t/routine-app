package kz.webapp.routine.model.dto

import java.time.LocalDate


data class AddTaskDto (
    var task: String = "",
    var comment: String = "",
    var performDate: LocalDate = LocalDate.now(),
)

data class UpdateTaskDto (
    var taskId: Int = 0,
    var task: String = "",
    var comment: String = "",
    var status: String = "",
    var createDate: LocalDate = LocalDate.now(),
)

data class PostponeTaskDto (
    var performDate: LocalDate
)