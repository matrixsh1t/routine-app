package kz.webapp.routine.controller

import kz.webapp.routine.service.Utils
import kz.webapp.routine.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainPageController(
    val taskService: TaskService,
    val utils: Utils,
    ) {
    @GetMapping("")
    fun showMainPage(model: Model): String {
        model.addAttribute("currentUser", utils.getCurrentUser("userName"))
        return "index"
    }
}