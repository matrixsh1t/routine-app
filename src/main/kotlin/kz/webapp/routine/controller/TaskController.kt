package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.dto.UpdateTaskDto
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@Controller
class MainController(
    val taskService: TaskService

    ) {
    @GetMapping("/")
    fun showTodaysTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showTodaysTasks())
        model.addAttribute("title", "Список задач на сегодня")
        model.addAttribute("taskNum", taskService.showTodaysTasks().size)
        return "index"
    }

    @GetMapping("/tomorrows-tasks")
    fun showTomorrowsTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showTomorrowsTasks())
        model.addAttribute("title", "Список задач на завтра")
        model.addAttribute("taskNum", taskService.showTomorrowsTasks().size)
        return "index"
    }

    @GetMapping("/next-weeks-tasks")
    fun showNextWeeksTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showNextWeeksTasks())
        model.addAttribute("title", "Список задач на следующуюю неделю")
        model.addAttribute("taskNum", taskService.showNextWeeksTasks().size)
        return "index"
    }

    @GetMapping("/next-months-tasks")
    fun showNextMonthsTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showNextMonthsTasks())
        model.addAttribute("title", "Список задач на следующий месяц")
        model.addAttribute("taskNum", taskService.showNextMonthsTasks().size)
        return "index"
    }

    @GetMapping("/all-active")
    fun showAllActiveTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showAllActiveTasks())
        model.addAttribute("title", "Список активных задач")
        model.addAttribute("taskNum", taskService.showAllActiveTasks().size)
        return "index"
    }

    @GetMapping("/all")
    fun showAllTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showAllTasks())
        model.addAttribute("title", "Список всех задач")
        model.addAttribute("taskNum", taskService.showAllTasks().size)
        return "index"
    }

    @GetMapping("/create")
    fun showSaveTaskPage(model: Model): String {
        val addTaskDto = AddTaskDto("","", LocalDate.now())
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
        taskService.moveTaskToAnotherDate(id,"day")
        return "redirect:/"
    }

    @GetMapping("/next-week/{id}")
    fun moveTaskToNextWeek(@PathVariable("id") id: Int): String {
        taskService.moveTaskToAnotherDate(id,"week")
        return "redirect:/"
    }

    @GetMapping("/next-month/{id}")
    fun moveTaskToNextMonth(@PathVariable("id") id: Int): String {
        taskService.moveTaskToAnotherDate(id,"month")
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

    @GetMapping("/close/{id}")
    fun closeTask(@PathVariable("id") id: Int): String {
        taskService.closeTask(id)
        return "redirect:/"
    }
}