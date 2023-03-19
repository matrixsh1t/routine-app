package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.dto.UpdateTaskDto
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@Controller
class MainController(
    val taskService: TaskService
    ) {
    @GetMapping("/")
    fun showAllTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showTasks())
        return "index"
    }

    @GetMapping("/create")
    fun showSaveTaskPage(model: Model): String {
        val addTaskDto = AddTaskDto("","", LocalDateTime.now())
        model.addAttribute("addTaskDto", addTaskDto)
        return "create"
    }

    @PostMapping("/create")
    fun saveTask(@ModelAttribute("addTaskDto") addTaskDto: AddTaskDto): String {
        taskService.addTask(addTaskDto)
        return "redirect:/"
    }

    @GetMapping("/delete/{id}")
//    @Throws(EntityNotFoundException::class)
    fun deleteTask(@PathVariable("id") id: Int): String {
        taskService.deleteTaskById(id)
        return "redirect:/"
    }

    @GetMapping("/tomorrow/{id}")
    fun moveTaskToTomorrow(@PathVariable("id") id: Int): String {
        taskService.moveTaskToTomorrow(id)
        return "redirect:/"
    }

    @GetMapping("/update/{id}")
    fun showUpdateTaskPage(@PathVariable("id") id: Int, model: Model): String {
        val updateTaskDto = taskService.findTaskById(id)
        model.addAttribute("updateTaskDto", updateTaskDto)
        return "update"
    }

    @PostMapping("/update/{id}")
    fun updateTask(@PathVariable("id") id: Int, @ModelAttribute("updateTaskDto") updateTaskDto: UpdateTaskDto): String {
        taskService.updateTask(id, updateTaskDto)
        return "redirect:/"
    }
}