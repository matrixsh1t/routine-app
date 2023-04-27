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
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*


@Service
class TaskServiceImpl(val taskRepo: TaskRepo, val accountRepo: AccountRepo): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    override fun showTodaysTasks(): List<TaskEntity> {
        return taskRepo.findAllTodaysTasks().ifEmpty {
            val msg = "There are no tasks for today found"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun showTomorrowsTasks(): List<TaskEntity> {
        return taskRepo.findAllTomorrowsTasks().ifEmpty {
            val msg = "There are no tasks for tomorrow found"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun showNextWeeksTasks(): List<TaskEntity> {
        return taskRepo.findAllNextWeeksTasks().ifEmpty {
            val msg = "There are no tasks for next week found"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun showNextMonthsTasks(): List<TaskEntity> {
        return taskRepo.findAllNextMonthsTasks().ifEmpty {
            val msg = "There are no tasks for next month found"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun showAllActiveTasks(): List<TaskEntity> {
        return taskRepo.findAllActiveTasks().ifEmpty {
            val msg = "There are no active tasks found"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun showAllTasks(): List<TaskEntity> {
        return taskRepo.findAllTasks().ifEmpty {
            val msg = "There are no tasks found at all"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun addTask(addTaskDto: AddTaskDto) {
        val addTaskEntity = TaskEntity(
            taskId = 0,
            task = addTaskDto.task,
            comment = addTaskDto.comment,
            responsible = addTaskDto.responsible,
            dueDate = LocalDate.now(),
            createDate = LocalDate.now(),
            closeDate = LocalDate.now(),
            status = "a",
            userName = getCurrentUser()
        )

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

        if (updateTaskEntity != null) {
            val updateTaskEntity = TaskEntity(
                taskId = id,
                task = updateTaskDto.task,
                comment = updateTaskDto.comment,
                responsible = updateTaskDto.responsible,
                dueDate = updateTaskDto.dueDate,
                createDate = updateTaskDto.createDate,
                closeDate = updateTaskDto.closeDate,
                status = updateTaskDto.status,
                userName = updateTaskDto.userName
            )

            //saves entity with try-catch and logs
            if (updateTaskEntity.userName in getAllUserNamesFromDb()) {
                entitySaveTryCatchBlock(
                    updateTaskEntity,
                    "Task ${updateTaskEntity.taskId} is updated",
                    "Failed to update task with id ${updateTaskEntity.taskId}"
                )
            } else {
                logger.error("${updateTaskEntity.userName} is not in the list of accounts ${getAllUserNamesFromDb()}, " +
                        "so the task is not updated")
            }
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
                responsible = updateTimeEntity.responsible,
                dueDate = newDate,
                createDate = updateTimeEntity.createDate,
                closeDate = LocalDate.now(),
                status = updateTimeEntity.status,
                userName = updateTimeEntity.userName)

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
                responsible = closeTaskEntity.responsible,
                dueDate = closeTaskEntity.dueDate,
                createDate = closeTaskEntity.createDate,
                closeDate = LocalDate.now(),
                status = "x",
                userName = closeTaskEntity.userName)

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(closeTaskEntity,
                "Task ${closeTaskEntity.taskId} is closed",
                "Failed to closed task with id ${closeTaskEntity.taskId}")
        }
    }

    override fun getCurrentUser(): String {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            return authentication.name
        } catch (e: AccountException) {
            val msg = "Failed to get current user"
            logger.error(msg)
            logger.error(e.message)
            throw (TaskException(msg))
        }
    }

    //------------------ private functions block ------------------

    //saves entity with try-catch block and makes logging
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

            if (accountEntity.isEmpty) {
                val msg = "There is no account with username $userName"
                throw AccountNotExistsException(msg)
            } else {
                return accountEntity.get()
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
     * --- to provide them to frontend to choose a $userName
     * --- to check if the account username exists before saving the task
     */

    private fun getAllUserNamesFromDb(): List<String> {
        try {
            val userNames = accountRepo.findAllUserNamesFromDb()

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
    fun getDateFromWeekNumber(weekNumber: Int): LocalDate {
        // Get the ISO week fields
        val weekFields = WeekFields.of(Locale.getDefault())
        // Get the first day of the first week of the year
        val firstDayOfYear = LocalDate.now().with(weekFields.weekOfYear(), 1)
            .with(weekFields.dayOfWeek(), 1)
        // Add the number of weeks to get the target week
        val targetWeek = firstDayOfYear.plusWeeks(weekNumber.toLong() - 1)

        return targetWeek
    }


}