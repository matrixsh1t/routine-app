package kz.webapp.routine.service

import kz.webapp.routine.model.dto.*
import kz.webapp.routine.model.entity.TaskEntity
import java.time.LocalDate


interface TaskService {
    fun showTodaysTasks(): List<TaskEntity>
    fun showTomorrowsTasks(): List<TaskEntity>
    fun showNextWeeksTasks(): List<TaskEntity>
    fun showNextMonthsTasks(): List<TaskEntity>
    fun showAllActiveTasks(): List<TaskEntity>
    fun showAllTasks(): List<TaskEntity>
    fun addTask(addTaskDto: AddTaskDto)
    fun deleteTaskById(id: Int)
    fun updateTask(id: Int, updateTaskDto: UpdateTaskDto)
    fun findTaskById(id: Int): TaskEntity?
    //changes task perform_date to date offset
    fun moveTaskToAnotherDate(id: Int, period: String)
    //changes task status to 'x'
    fun closeTask(id: Int)
    fun getCurrentUser(): String?

}