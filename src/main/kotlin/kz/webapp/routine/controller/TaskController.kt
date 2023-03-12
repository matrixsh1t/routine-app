package kz.webapp.routine.controller

import jakarta.persistence.EntityNotFoundException
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class MainController(
    val taskService: TaskService
    ) {
    @GetMapping("/")
    fun test(model: Model): String {
        model.addAttribute("tasks", taskService.showTasks())
        return "index"
    }
    @GetMapping("/create")
    fun saveTaskPage(model: Model): String {
        val addTaskDto = AddTaskDto(0,"","")
        model.addAttribute("addTaskDto", addTaskDto)
        return "create"
    }

    @PostMapping("/create")
    fun saveTask(@ModelAttribute("addTaskDto") addTaskDto: AddTaskDto): String {
        taskService.addTask(addTaskDto)
        return "redirect:/"
    }

    @GetMapping("/delete/{id}")
    @Throws(EntityNotFoundException::class)
    fun deleteTask(@PathVariable("id") id: Int): String {
        taskService.deleteUserById(id)
        return "redirect:/"
    }

    @GetMapping("/update/{id}")
    fun updateTaskPage(@PathVariable("id") id: Int, model: Model): String {
        val addTaskDto = taskService.findByIdOrNull(id)
        model.addAttribute("addTaskDto", addTaskDto)
        return "update"
    }

    @PostMapping("/update/{id}")
    fun updateTask(@PathVariable("id") id: Int, @ModelAttribute("addTaskDto") addTaskDto: AddTaskDto): String {
        taskService.updateTask(id, addTaskDto)
        return "redirect:/"
    }

}