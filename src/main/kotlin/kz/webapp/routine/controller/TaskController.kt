package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime

@Controller
class MainController(
    val taskService: TaskService
    ) {
    @GetMapping("/main")
    fun test(model: Model): String {
        model.addAttribute("tasks", taskService.showTasks())
        return "index"
    }
    @GetMapping("/")
    fun saveTaskPage(model: Model): String {
//        val addTaskDto = AddTaskDto()
//        model.addAttribute("addTaskDto", addTaskDto)
        return "save"
    }
    @PostMapping("/")
    fun saveTask(@ModelAttribute("addTaskDto") addTaskDto: AddTaskDto): String {
//        taskService.addTask(addTaskDto)
        println("printout----------------${addTaskDto}")
    return "redirect:/"

    }


}