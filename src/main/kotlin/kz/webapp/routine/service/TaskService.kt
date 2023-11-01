package kz.webapp.routine.service

import kz.webapp.routine.model.dto.*
import kz.webapp.routine.model.entity.TaskEntity


interface TaskService {
    fun showTodaysTasks(): List<TaskEntity>
    fun showTomorrowsTasks(): List<TaskEntity>
    fun showNextWeeksTasks(): List<TaskEntity>
    fun showNextMonthsTasks(): List<TaskEntity>
    fun showAllActiveTasks(): List<TaskEntity>
    fun showAllClosedTasks(): List<TaskEntity>
    fun showAllActiveTasksOfCurrentUser(): List<TaskEntity>
    fun showAllClosedTasksOfCurrentUser(): List<TaskEntity>
    fun showAllTasksOfCurrentUser(): List<TaskEntity>
    fun showAllTasks(): List<TaskEntity>
    fun addTask(addTaskDto: AddTaskDto)
    fun deleteTaskById(id: Int)
    fun updateTask(id: Int, updateTaskDto: UpdateTaskDto)
    fun findTaskById(id: Int): TaskEntity?

    //changes task perform_date to date offset
    fun moveTaskToAnotherDate(id: Int, period: String)

    //changes task status to 'x' - closed
    fun closeTask(id: Int)
    fun getListOfResponsiblesFromDb(): List<String>

    // all tasks which have serchstring in Task or Comment or City cell
    fun searchInDb(searchString: String): List<TaskEntity>

    fun activateTask(id: Int)

    // all tasks by TagName of all users (for Admin)
    fun getAllTasksByTagName(tagName: String): List<TaskEntity>

    // all active tasks by TagName of current user
    fun getAllTasksByTagNameOfCurrentUser(tagName: String): List<TaskEntity>

}