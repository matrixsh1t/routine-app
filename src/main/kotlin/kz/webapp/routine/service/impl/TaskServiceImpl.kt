package kz.webapp.routine.service.impl

import jakarta.persistence.EntityNotFoundException
import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.repository.TaskRepo
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*


@Service
class TaskServiceImpl(val taskRepo: TaskRepo): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    override fun showTasks(): List<TaskEntity> {
        return taskRepo.findAll()
    }

    override fun addTask(addTaskDto: AddTaskDto) {
        val addTaskEntity = TaskEntity(
            taskId = 0,
            task = addTaskDto.task,
            comment = addTaskDto.comment
        )
        try {
            if (taskRepo.findById(addTaskDto.taskId).isEmpty) {
                taskRepo.save(addTaskEntity)
                logger.info("Successfully created new task with ID ${addTaskEntity.taskId}")
            }
        }
        catch(e: TaskException) {
            val msg = "Failed to create task id ${addTaskEntity.taskId}"
            logger.error(msg)
            logger.error(e.message)
            throw (TaskException(msg))
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun deleteUserById(id: Int) {
        val task = taskRepo.findById(id)
        if (task.isPresent) {
            taskRepo.deleteById(id)
            logger.info("The task with ID $id was successfully deleted")
        } else {
            throw EntityNotFoundException("no task found")
        }
    }
    override fun findTaskById(id: Int): Optional<TaskEntity> {
        return taskRepo.findById(id)
    }
}