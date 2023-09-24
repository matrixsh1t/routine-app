package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.dto.UpdateTaskDto
import kz.webapp.routine.service.Utils
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import kz.webapp.routine.model.enums.City
import java.time.LocalDate


@Controller
@RequestMapping("/todo")
class MainController(
    val taskService: TaskService,
    val utils: Utils,
    ) {

    // active tasks for today of current user
    @GetMapping("/today-tasks")
    fun showTodaysTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showTodaysTasks())
        model.addAttribute("title", "Список задач на сегодня")
        model.addAttribute("taskNum", taskService.showTodaysTasks().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // active tasks for tomorrow of current user
    @GetMapping("/tomorrow-tasks")
    fun showTomorrowsTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showTomorrowsTasks())
        model.addAttribute("title", "Список задач на завтра")
        model.addAttribute("taskNum", taskService.showTomorrowsTasks().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // active tasks for next week of current user
    @GetMapping("/next-week-tasks")
    fun showNextWeeksTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showNextWeeksTasks())
        model.addAttribute("title", "Список задач на следующуюю неделю")
        model.addAttribute("taskNum", taskService.showNextWeeksTasks().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // active tasks for next month of current user
    @GetMapping("/next-month-tasks")
    fun showNextMonthsTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showNextMonthsTasks())
        model.addAttribute("title", "Список задач на следующий месяц")
        model.addAttribute("taskNum", taskService.showNextMonthsTasks().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // all active tasks of all users (for Admin)
    @GetMapping("/all-act-tasks")
    fun showAllActiveTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showAllActiveTasks())
        model.addAttribute("title", "Список активных задач всех пользователей")
        model.addAttribute("taskNum", taskService.showAllActiveTasks().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // all active tasks of current user
    @GetMapping("/user-act-tasks")
    fun showCurrentUserActiveTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showAllActiveTasksOfCurrentUser())
        model.addAttribute("title", "Список ваших активных задач")
        model.addAttribute("taskNum", taskService.showAllActiveTasksOfCurrentUser().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // all tasks of current user (closed and active)
    @GetMapping("/user-all-tasks")
    fun showCurrentUserAllTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showAllTasksOfCurrentUser())
        model.addAttribute("title", "Список всех ваших задач")
        model.addAttribute("taskNum", taskService.showAllTasksOfCurrentUser().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    // all tasks of all users(closed, active, cancelled)
    @GetMapping("/all-tasks")
    fun showAllTasksPage(model: Model): String {
        model.addAttribute("tasks", taskService.showAllTasks())
        model.addAttribute("title", "Список всех задач всех пользователей")
        model.addAttribute("taskNum", taskService.showAllTasks().size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }

    @GetMapping("/create")
    fun showSaveTaskPage(model: Model): String {
        val addTaskDto = AddTaskDto(
            city = City.Pavlodar,
            dueDate = LocalDate.now().toString(),
            account = utils.getCurrentUser("userName"))
        val cities = City.values()

        val responsibles = taskService.getListOfResponsiblesFromDb()
        model.addAttribute("addTaskDto", addTaskDto)
        model.addAttribute("responsibles", responsibles)
        model.addAttribute("currentUser", utils.getCurrentUser("userName"))
        model.addAttribute("cities", cities)
        return "create-task"
    }

    @PostMapping("/create")
    fun saveTask(@ModelAttribute("addTaskDto") addTaskDto: AddTaskDto): String {
        taskService.addTask(addTaskDto)
        return if (utils.getCurrentUser("userName") == "admin") {
            "redirect:/todo/today-tasks"
        } else {
            "redirect:/todo/user-act-tasks"
        }
    }

    @GetMapping("/delete/{id}")
//    @Throws(EntityNotFoundException::class)
    fun deleteTask(@PathVariable("id") id: Int): String {
        taskService.deleteTaskById(id)
        return "redirect:/todo/today-tasks"
    }

    @GetMapping("/tomorrow/{id}")
    fun moveTaskToTomorrow(@PathVariable("id") id: Int): String {
        taskService.moveTaskToAnotherDate(id,"day")
        return "redirect:/todo/today-tasks"
    }

    @GetMapping("/next-week/{id}")
    fun moveTaskToNextWeek(@PathVariable("id") id: Int): String {
        taskService.moveTaskToAnotherDate(id,"week")
        return "redirect:/todo/today-tasks"
    }

    @GetMapping("/next-month/{id}")
    fun moveTaskToNextMonth(@PathVariable("id") id: Int): String {
        taskService.moveTaskToAnotherDate(id,"month")
        return "redirect:/todo/today-tasks"
    }

    @GetMapping("/update/{id}")
    fun showUpdateTaskPage(@PathVariable("id") id: Int, model: Model): String {
        val updateTaskDto = taskService.findTaskById(id)
        val responsibles = taskService.getListOfResponsiblesFromDb()
        val cities = City.values()

        model.addAttribute("responsibles", responsibles)
        model.addAttribute("updateTaskDto", updateTaskDto)
        model.addAttribute("cities", cities)
        return "update-task"
    }

    @PostMapping("/update/{id}")
    fun updateTask(@PathVariable("id") id: Int, @ModelAttribute("updateTaskDto") updateTaskDto: UpdateTaskDto): String {
        taskService.updateTask(id, updateTaskDto)
        return if (utils.getCurrentUser("userName") == "admin") {
            "redirect:/todo/today-tasks"
        } else {
            "redirect:/todo/user-act-tasks"
        }
    }

    @GetMapping("/close/{id}")
    fun closeTask(@PathVariable("id") id: Int): String {
        taskService.closeTask(id)
        return if (utils.getCurrentUser("userName") == "admin") {
            "redirect:/todo/today-tasks"
        } else {
            "redirect:/todo/user-act-tasks"
        }
    }

    // all tasks which have searchstring in Task or Comment or City cell of admin or other users
    @GetMapping("/search")
    fun showSearchResultPage(@RequestParam("searchString") searchString: String, model: Model): String {
        model.addAttribute("tasks", taskService.searchInDb(searchString))
        model.addAttribute("title", "Найденные записи")
        model.addAttribute("taskNum", taskService.searchInDb(searchString).size)
        model.addAttribute("currentUserName", utils.getCurrentUser("userName"))
        return "show-task"
    }
}