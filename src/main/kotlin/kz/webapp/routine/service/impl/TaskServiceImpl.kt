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
            try {
                taskRepo.save(updateTaskEntity)
                logger.info("Task ${updateTaskEntity.taskId} is updated")
            } catch(e: TaskException) {
                val msg = "Failed to update task with id ${updateTaskEntity.taskId}"
                logger.error(msg)
                logger.error(e.message)
                throw (TaskException(msg))
            }
        }
    }

    override fun moveTaskToTomorrow(id: Int) {
            val updateTimeEntity = taskRepo.findByIdOrNull(id)
            val oneDayPeriod = Period.of(0, 0, 1)
            if (updateTimeEntity != null) {
                val updateTimeEntity = TaskEntity(
                    taskId = id,
                    task = updateTimeEntity.task,
                    comment = updateTimeEntity.comment,
                    performDate = updateTimeEntity.performDate.plus(oneDayPeriod)
                )
                try {
                    taskRepo.save(updateTimeEntity)
                    logger.info("Task ${updateTimeEntity.taskId} is moved to tomorrow")
                } catch(e: TaskException) {
                    val msg = "Failed to update task with id ${updateTimeEntity.taskId}"
                    logger.error(msg)
                    logger.error(e.message)
                    throw (TaskException(msg))
                }
            }
    }


}