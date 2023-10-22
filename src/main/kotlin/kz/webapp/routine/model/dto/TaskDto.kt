package kz.webapp.routine.model.dto

import kz.webapp.routine.model.enums.City
import java.time.LocalDate


data class AddTaskDto (
    var task: String = "",
    var comment: String = "",
    var city: City = City.General,
    var dueDate: String = "",
    var dueWeek: String = "",
    var account: String = "",
)

data class UpdateTaskDto (
    var id: Int = 0,
    var task: String = "",
    var comment: String = "",
    var city: City = City.General,
    var status: String = "a",
    var createDate: LocalDate = LocalDate.now(),
    var dueDate: String = "",
    var dueWeek: String = "",
    var userName: String = "",
    var selectedTags: List<String> = emptyList(),

)
