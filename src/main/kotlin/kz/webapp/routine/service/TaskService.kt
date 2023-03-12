package kz.webapp.routine.service

import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.entity.TaskEntity
import java.util.*


interface TaskService {

    fun showTasks(): List<TaskEntity>
    fun addTask(addTaskDto: AddTaskDto)
    fun deleteUserById(id: Int)
    fun findTaskById(id: Int): Optional<TaskEntity>
}