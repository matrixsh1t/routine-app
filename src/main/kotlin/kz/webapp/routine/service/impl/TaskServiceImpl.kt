package kz.webapp.routine.service.impl

import kz.webapp.routine.exception.AccountException
import kz.webapp.routine.exception.AccountNotExistsException
import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.exception.TaskNotExistsException
import kz.webapp.routine.model.dto.*
import kz.webapp.routine.model.entity.AccountEntity
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.repository.AccountRepo
import kz.webapp.routine.repository.TaskRepo
import kz.webapp.routine.service.ServiceFunctions
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class TaskServiceImpl(
    val taskRepo: TaskRepo,
    val accountRepo: AccountRepo,
    val serviceFunctions: ServiceFunctions
    ): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    // active tasks for today of current user
    override fun showTodaysTasks(): List<TaskEntity> {
        return taskRepo.findAllTodaysTasksOfCurrentUser(serviceFunctions.getCurrentUser("userName")).ifEmpty {
            val msg = "There are no tasks for today found"
            logger.error(msg)
            ArrayList()
        }
    }

    // active tasks for tomorrow of current user
    override fun showTomorrowsTasks(): List<TaskEntity> {
        val currentUser = serviceFunctions.getCurrentUser("userName")
        return taskRepo.findAllTomorrowsTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for tomorrow found"
            logger.error(msg)
            ArrayList()
        }
    }

    // active tasks for next week of current user
    override fun showNextWeeksTasks(): List<TaskEntity> {
        val currentUser = serviceFunctions.getCurrentUser("userName")
        return taskRepo.findAllNextWeeksTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for next week found"
            logger.error(msg)
            ArrayList()
        }
    }

    // active tasks for next month of current user
    override fun showNextMonthsTasks(): List<TaskEntity> {
        val currentUser = serviceFunctions.getCurrentUser("userName")
        return taskRepo.findAllNextMonthsTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for next month found"
            logger.error(msg)
            ArrayList()
        }
    }

    // all active tasks of all users (for Admin)
    override fun showAllActiveTasks(): List<TaskEntity> {
        return taskRepo.findAllActiveTasks().ifEmpty {
            val msg = "There are no active tasks for any user found"
            logger.error(msg)
            ArrayList()
        }
    }

    //all active tasks of current user
    override fun showAllActiveTasksOfCurrentUser(): List<TaskEntity> {
        val currentUser = serviceFunctions.getCurrentUser("userName")
        return taskRepo.findAllByAccountIdUsernameAndStatusEquals(currentUser).ifEmpty {
            val msg = "There are no active tasks found"
            logger.error(msg)
            ArrayList()
        }
    }

    // all tasks of current user (closed and active)
    override fun showAllTasksOfCurrentUser(): List<TaskEntity> {
        val currentUser = serviceFunctions.getCurrentUser("userName")
        return taskRepo.findAllByAccountIdUsernameOrderByDueDate(currentUser).ifEmpty {
            val msg = "There are no active tasks found"
            logger.error(msg)
            ArrayList()
        }
    }

    // all tasks of all users(closed, active, cancelled)
    override fun showAllTasks(): List<TaskEntity> {
        return taskRepo.findAllByOrderByAccountIdUsername().ifEmpty {
            val msg = "There are no tasks for all users found at all"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun addTask(addTaskDto: AddTaskDto) {
        //convert the date or week number to LocalDate
        val dueDate = parseDateFromFrontEnd(addTaskDto.dueDate)
        val account = accountRepo.findAccountEntityByUsername(addTaskDto.accountExecutor
            .ifEmpty { serviceFunctions.getCurrentUser("userName") })!!

        val addTaskEntity = TaskEntity(
            taskId = 0,
            task = addTaskDto.task,
            comment = addTaskDto.comment,
            city = addTaskDto.city,
            status = "a",
            createDate = LocalDate.now(),
            dueDate = dueDate,
            closeDate = null,
            accountId = account,

            //userName = getCurrentUser()
            //responsible = "roman", //getResponsibleOfCurrentAccount(),
        )

        //save entity with separate function of try-catch block
        entitySaveTryCatchBlock(addTaskEntity,
            "Successfully created new task with ID ${addTaskEntity.taskId}",
            "Failed to create task id ${addTaskEntity.taskId}")
    }

    override fun deleteTaskById(id: Int) {
        val task = taskRepo.findByIdOrNull(id)

        if (task != null) {
            taskRepo.deleteById(id)
            logger.info("The task with ID $id was successfully deleted")
        } else {
            val msg = "No task with id $id found"
            logger.error(msg)
            throw TaskNotExistsException(msg)
        }
    }

    override fun updateTask(id: Int, updateTaskDto: UpdateTaskDto) {
        val updateTaskEntity = taskRepo.findByIdOrNull(id)
        val dueDate = parseDateFromFrontEnd(updateTaskDto.dueDate)
        logger.error(dueDate.toString())

        if (updateTaskEntity != null) {
            val updateTaskEntity = TaskEntity(
                taskId = id,
                task = updateTaskDto.task,
                comment = updateTaskDto.comment,
                city = updateTaskDto.city,
                createDate = updateTaskDto.createDate,
                dueDate = dueDate,
                closeDate = null,
                status = updateTaskDto.status,
                accountId  = accountRepo.findAccountEntityByUsername(updateTaskDto.userName)!!
            )

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(
                updateTaskEntity,
                "Task ${updateTaskEntity.taskId} is updated",
                "Failed to update task with id ${updateTaskEntity.taskId}"
            )
//            if (updateTaskEntity.responsible in getAllResponsiblesFromDb()) {
//                entitySaveTryCatchBlock(
//                    updateTaskEntity,
//                    "Task ${updateTaskEntity.taskId} is updated",
//                    "Failed to update task with id ${updateTaskEntity.taskId}"
//                )
//            } else {
//                logger.error("${updateTaskEntity.responsible} is not in the list of accounts ${getAllResponsiblesFromDb()}, " +
//                        "so the task is not updated")
//            }
        }
    }

    override fun moveTaskToAnotherDate(id: Int, period: String) {
        val updateTimeEntity = taskRepo.findByIdOrNull(id)
        var newDate = LocalDate.now()

        //add period to change the date to make it monday
        when(period) {
            "day" -> {
                newDate = if (LocalDate.now().dayOfWeek.value >= 5) {
                    LocalDate.now().plusDays(8-LocalDate.now().dayOfWeek.value.toLong())
                } else LocalDate.now().plusDays(1)
            }
            "week" -> newDate = LocalDate.now().plusDays(8-LocalDate.now().dayOfWeek.value.toLong())
            "month" -> newDate = LocalDate.now().plusDays(29-LocalDate.now().dayOfWeek.value.toLong())
        }

        if (updateTimeEntity != null) {
            val updateTimeEntity = TaskEntity(
                taskId = id,
                task = updateTimeEntity.task,
                comment = updateTimeEntity.comment,
                city = updateTimeEntity.city,
                dueDate = newDate,
                createDate = updateTimeEntity.createDate,
                closeDate = LocalDate.now(),
                status = updateTimeEntity.status,
                accountId = serviceFunctions.getCurrentUserEntityByUserName()
            )

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(updateTimeEntity,
                "Task ${updateTimeEntity.taskId} is moved to tomorrow",
                "Failed to update task with id ${updateTimeEntity.taskId}")
        }
    }

    override fun findTaskById(id: Int): TaskEntity? {
        return taskRepo.findByIdOrNull(id)
    }

    override fun closeTask(id: Int) {
        val closeTaskEntity = taskRepo.findByIdOrNull(id)

        if (closeTaskEntity != null) {
            val closeTaskEntity = TaskEntity(
                taskId = id,
                task = closeTaskEntity.task,
                comment = closeTaskEntity.comment,
                city = closeTaskEntity.city,
                status = "x",
                createDate = closeTaskEntity.createDate,
                dueDate = closeTaskEntity.dueDate,
                closeDate = LocalDate.now(),
                accountId = serviceFunctions.getCurrentUserEntityByUserName()

                )

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(closeTaskEntity,
                "Task ${closeTaskEntity.taskId} is closed",
                "Failed to closed task with id ${closeTaskEntity.taskId}")
        }
    }

    override fun getListOfResponsiblesFromDb(): List<String> {
        return accountRepo.findAllResponsiblesFromDb()
    }

    //-------------------------------------------------------------
    //------------------ private functions block ------------------
    //-------------------------------------------------------------

    /** saves entity with try-catch block and makes logging */
    private fun entitySaveTryCatchBlock(entity: TaskEntity, msg: String, errMsg: String) {
        try {
            taskRepo.save(entity)
            logger.info(msg)
        } catch(e: TaskException) {
            logger.error(errMsg)
            logger.error(e.message)
            throw (TaskException(errMsg))
        }
    }

    private fun getAccountByUsername(userName: String?): AccountEntity {
        try {
            val accountEntity = accountRepo.findAccountEntityByUsername(userName)

            if (accountEntity == null) {
                val msg = "There is no account with username $userName"
                throw AccountNotExistsException(msg)
            } else {
                return accountEntity
            }
        } catch (e: AccountNotExistsException) {
            logger.error(e.message)
            throw AccountNotExistsException(e.message)
        } catch (e: Exception) {
            val msg = "Failed to execute query in account table"
            logger.error(msg)
            logger.error(e.message)
            throw AccountException(msg)
        }
    }

    /** get all usernames from AccountEntity
     * to provide them to frontend to choose a $userName
     * to check if the account username exists before saving the task */
    private fun getAllResponsiblesFromDb(): List<String> {
        try {
            val userNames = accountRepo.findAllResponsiblesFromDb()

            if (userNames.isEmpty()) {
                val msg = "Cannot get a list of users from DB"
                throw Exception(msg)
            } else {
                logger.info("List of users $userNames")
                return userNames
            }
        } catch (e: Exception) {
            val msg = "Cannot get a list of users from DB"
            logger.error(msg)
            logger.error(e.message)
            throw Exception(msg)
        }
    }

    /** Parse date from widget week and date */
    private fun parseDateFromFrontEnd(dateOrWeek: String): LocalDate {
        val dueDateList: List<String>
        val dueDate: LocalDate

        // if no date is present then set the date to today
        if(dateOrWeek.isBlank()) {
            dueDate = LocalDate.now()
            logger.error("Invalid date or week number format so set it to TODAY")
        // if dueDateList is " , " then set the date to today
        } else if(dateOrWeek == ",") {
            dueDate = LocalDate.now()
            logger.error("Invalid date or week number format so set it to TODAY")
        // if there is date in the format "2023-07-17, " or " ,2023-W06"
        } else {
            dueDateList = dateOrWeek.split(",")
            // if dueDateList has not 2 members
            if (dueDateList.size != 2) {
                dueDate = LocalDate.now()
                logger.error("Invalid date or week number format so set it to TODAY")
            } else {
                // if comes the DATE data
                dueDate = if (dueDateList[0].isNotBlank()) {
                    LocalDate.parse(dueDateList[0], DateTimeFormatter.ISO_LOCAL_DATE)
                    // if comes the WEEK data
                } else {
                    // get "06" from "2023-W06"
                    getDateFromWeekNumber(dueDateList[1].substring(6, 8).toInt())
                }
            }
        }
        return dueDate
    }

    private fun getDateFromWeekNumber(weekNumber: Int): LocalDate {
        val firstDayOfYear = LocalDate.of(LocalDate.now().year, 1, 1)
        val firstWeek = firstDayOfYear.dayOfWeek.value
        val daysPassed = (8 - firstWeek).toLong()
        val firstMonday = firstDayOfYear.plusDays(daysPassed)
        return firstMonday.plusWeeks(weekNumber.toLong() - 1)
    }

//    private fun getResponsibleOfCurrentAccount(): String {
//        return accountRepo.findResponsibleByUsername(getCurrentUser())
//    }
}