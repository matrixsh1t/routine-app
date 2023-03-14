package kz.webapp.routine.service.impl

import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.exception.TaskNotExistsException
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.dto.UpdateTaskDto
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.repository.TaskRepo
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class TaskServiceImpl(val taskRepo: TaskRepo): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    override fun showTasks(): List<TaskEntity> {
        return taskRepo.findAll().ifEmpty {
            val msg = "There are no task found"
            logger.error(msg)
            throw TaskNotExistsException(msg)
        }
    }

    override fun addTask(addTaskDto: AddTaskDto) {
        val addTaskEntity = TaskEntity(
            taskId = 0,
            task = addTaskDto.task,
            comment = addTaskDto.comment
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
            throw TaskNotExistsException("No such task found")
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
                comment = updateTaskDto.comment
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
}