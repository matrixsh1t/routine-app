package kz.webapp.routine.service.impl

import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.repository.TaskRepo
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class TaskServiceImpl(val taskRepo: TaskRepo): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    override fun showTasks(): List<TaskEntity> {
        return taskRepo.findAll()
    }

    override fun addTask(addTaskDto: AddTaskDto): String {
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
        return "-------------------"
    }
    override fun addTask1(entity: TaskEntity): TaskEntity {
        val entity = entity
        try {
            taskRepo!!.save(entity)
            logger.info("Successfully created new task with ID ${entity.taskId}")
        } catch(e: TaskException) {
            val msg = "Failed to create task id ${entity.taskId}"
            logger.error(msg)
            logger.error(e.message)
            throw (TaskException(msg))
        }
        return entity
    }
}