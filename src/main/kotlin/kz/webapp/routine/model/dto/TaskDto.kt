package kz.webapp.routine.model.dto

import java.time.LocalDate


data class AddTaskDto (
    var task: String = "",
    var comment: String = "",
    var performDate: LocalDate = LocalDate.now(),
    var responsible: String = "",
    var dueDate: Int = 0,
)

data class UpdateTaskDto (
    var taskId: Int = 0,
    var task: String = "",
    var comment: String = "",
    var status: String = "a",
    var createDate: LocalDate = LocalDate.now(),
    var dueDate: LocalDate = LocalDate.now(),
    var closeDate: LocalDate = LocalDate.now(),
    var responsible: String = "",
    var userName: String = "",
)
