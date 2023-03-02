package kz.webapp.routine.service

import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.entity.TaskEntity


interface TaskService {

    fun showTasks(): List<TaskEntity>

    fun addTask(addTaskDto: AddTaskDto): TaskEntity
}