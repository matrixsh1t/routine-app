package kz.webapp.routine.model.dto

import java.time.LocalDateTime


data class AddTaskDto (
    val task: String,
    val comment: String,
    val status: String,
    val week: Int,
    val city: String,
    val dateDue: LocalDateTime,

)
