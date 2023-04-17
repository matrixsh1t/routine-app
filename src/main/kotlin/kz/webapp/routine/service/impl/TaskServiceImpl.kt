package kz.webapp.routine.service.impl

import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.exception.TaskNotExistsException
import kz.webapp.routine.model.dto.*
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.repository.TaskRepo
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*


@Service
class TaskServiceImpl(val taskRepo: TaskRepo): TaskService {

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
            status = "a")

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
            )

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(updateTaskEntity,
            "Task ${updateTaskEntity.taskId} is updated",
            "Failed to update task with id ${updateTaskEntity.taskId}")
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
                status = updateTimeEntity.status)

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
            )

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(closeTaskEntity,
                "Task ${closeTaskEntity.taskId} is closed",
                "Failed to closed task with id ${closeTaskEntity.taskId}")
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