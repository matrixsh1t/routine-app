package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddAccDto
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.service.AccountService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class AccountController (
    val accountService: AccountService
        ){
//    @RequestMapping("/account")

    @GetMapping("/account/register")
    fun registerAccountPage(): String {
        return "register"
    }

    @PostMapping("/account/register")
    fun registerAccount(@ModelAttribute("addAccDto") addAccDto: AddAccDto): String {
        accountService.addAccount(addAccDto)
        return "redirect:/"
    }
}