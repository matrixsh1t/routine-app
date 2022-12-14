package kz.webapp.routine.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class MainController {
    @RequestMapping("/main")
    fun index(): String{
        return "index"
    }
}