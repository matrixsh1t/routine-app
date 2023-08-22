package kz.webapp.routine.model.dto

import kz.webapp.routine.model.entity.AccountEntity
import kz.webapp.routine.model.enums.City
import kz.webapp.routine.model.enums.Role
import java.time.LocalDate


data class AddTaskDto (
    var task: String = "",
    var comment: String = "",
    var city: City = City.Pavlodar,
    var performDate: LocalDate = LocalDate.now(),
    var dueDate: String = "",
    var accountId: AccountEntity = AccountEntity(4,"max","max","max", role = Role.ADMIN,"Maxat"),
)

data class UpdateTaskDto (
    var taskId: Int = 0,
    var task: String = "",
    var comment: String = "",
    var city: City,
    var status: String = "a",
    var createDate: LocalDate = LocalDate.now(),
    var dueDate: LocalDate = LocalDate.now(),
    var closeDate: LocalDate = LocalDate.now(),
//    var responsible: String = "",
//    var userName: String = "",
)
