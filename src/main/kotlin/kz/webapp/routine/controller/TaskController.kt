package kz.webapp.routine.controller

import jakarta.persistence.EntityNotFoundException
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping


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
        val addTaskDto = AddTaskDto("","")
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

}