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

import java.time.Period


@Service
class TaskServiceImpl(val taskRepo: TaskRepo): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    override fun showTasks(): List<TaskEntity> {
        return taskRepo.findAllTodaysTasks().ifEmpty {
            val msg = "There are no tasks found"
            logger.error(msg)
            throw TaskNotExistsException(msg)
        }
    }

    override fun addTask(addTaskDto: AddTaskDto) {
        val addTaskEntity = TaskEntity(
            taskId = 0,
            task = addTaskDto.task,
            comment = addTaskDto.comment,
            performDate = LocalDate.now()
        )
        try {
            taskRepo.save(addTaskEntity)
            logger.info("Successfully created new task with ID ${addTaskEntity.taskId}")
        } catch(e: TaskException) {
            val msg = "Failed to create task id ${addTaskEntity.taskId}"
            logger.error(msg)
            logger.error(e.message)
            throw (TaskException(msg))
        }
    }

    override fun deleteTaskById(id: Int) {
        val task = taskRepo.findById(id)
        if (task.isPresent) {
            taskRepo.deleteById(id)
            logger.info("The task with ID $id was successfully deleted")
        } else {
            val msg = "No task with id $id found"
            logger.error(msg)
            throw TaskNotExistsException(msg)
        }
    }

    override fun findTaskById(id: Int): TaskEntity? {
        return taskRepo.findByIdOrNull(id)
    }

    override fun updateTask(id: Int, updateTaskDto: UpdateTaskDto) {
        val updateTaskEntity = taskRepo.findByIdOrNull(id)
        if (updateTaskEntity != null) {
            val updateTaskEntity = TaskEntity(
                taskId = id,
                task = updateTaskDto.task,
                comment = updateTaskDto.comment,
                performDate = LocalDate.now()
            )
            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(updateTaskEntity,
            "Task ${updateTaskEntity.taskId} is updated",
            "Failed to update task with id ${updateTaskEntity.taskId}")
        }
    }

    override fun moveTaskToTomorrow(id: Int) {
            val updateTimeEntity = taskRepo.findByIdOrNull(id)

            var dayPeriod = Period.of(0, 0, 1)
            if (LocalDate.now().dayOfWeek.toString() == "FRIDAY") {
                dayPeriod = Period.of(0,0,3)
            }
            if (updateTimeEntity != null) {
                val updateTimeEntity = TaskEntity(
                    taskId = id,
                    task = updateTimeEntity.task,
                    comment = updateTimeEntity.comment,
                    performDate = LocalDate.now().plus(dayPeriod)
                )
                //saves entity with try-catch and logs
                entitySaveTryCatchBlock(updateTimeEntity,
                    "Task ${updateTimeEntity.taskId} is moved to tomorrow",
                    "Failed to update task with id ${updateTimeEntity.taskId}")
            }
    }
    override fun moveTaskToDate(id: Int, period: String) {
        val updateTimeEntity = taskRepo.findByIdOrNull(id)
        var newDate = LocalDate.now()

        if (period == "day") {
            newDate = LocalDate.now().plusDays(1)
            if (LocalDate.now().dayOfWeek.toString() == "FRIDAY") newDate = LocalDate.now().plusDays(3)
        }

        if (period == "week") {
            newDate = LocalDate.now().plusDays(8-LocalDate.now().dayOfWeek.value.toLong())
        }
        if (updateTimeEntity != null) {
            val updateTimeEntity = TaskEntity(
                taskId = id,
                task = updateTimeEntity.task,
                comment = updateTimeEntity.comment,
                performDate = newDate
            )
            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(updateTimeEntity,
                "Task ${updateTimeEntity.taskId} is moved to tomorrow",
                "Failed to update task with id ${updateTimeEntity.taskId}")
        }
    }

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
}