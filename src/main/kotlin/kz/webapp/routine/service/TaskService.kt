package kz.webapp.routine.service

import kz.webapp.routine.model.dto.*
import kz.webapp.routine.model.entity.TaskEntity


interface TaskService {
    fun showTasks(): List<TaskEntity>
    fun addTask(addTaskDto: AddTaskDto)
    fun deleteTaskById(id: Int)
    fun updateTask(id: Int, updateTaskDto: UpdateTaskDto)
    //changes task perform_date to date offset
    fun moveTaskToAnotherDate(id: Int, period: String)
}