package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddAccDto
import kz.webapp.routine.service.AccountService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/admin")
class AccountController (
    val accountService: AccountService
        ){

    @GetMapping("/account-register")
    fun registerAccountPage(): String {
        return "create-account"
    }

    @PostMapping("/account-register")
    fun registerAccount(@ModelAttribute("addAccDto") addAccDto: AddAccDto): String {
        accountService.addAccount(addAccDto)
        return "redirect:/admin"
    }

    @GetMapping("")
    fun showAdminPage(model:Model): String {
        model.addAttribute("accounts", accountService.showAccountList())
        return "admin-panel"
    }

    @GetMapping("/delete/{id}")
    fun deleteAccount(@PathVariable("id") id: Int): String {
        accountService.deleteAccountById(id)
        return "redirect:/admin"
    }
}