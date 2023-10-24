package kz.webapp.routine.controller

import kz.webapp.routine.model.dto.AddTagDto
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.enums.City
import kz.webapp.routine.service.TagService
import kz.webapp.routine.service.TaskService
import kz.webapp.routine.service.Utils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@Controller
@RequestMapping("/admin/tags")
class TagController(
    val tagService: TagService,
    val utils: Utils,
) {

    @GetMapping("/all-tags")
    fun showAllTagsPage(model: Model): String {
        val tags = tagService.showTags()
        val currentUserName = utils.getCurrentUser("userName")
        val tagList = utils.getAllTags()

        model.addAttribute("tagList", tagList)
        model.addAttribute("currentUserName", currentUserName)
        model.addAttribute("tags", tags)
        return "show-tag"
    }

    @GetMapping("/create")
    fun showSaveTagPage(model: Model): String {

        val tagList = utils.getAllTags()

        model.addAttribute("tagList", tagList)
        return "create-tag"
    }

    @PostMapping("/create")
    fun saveTag(@ModelAttribute("addTagDto") addTagDto: AddTagDto): String {
        tagService.addTag(addTagDto)
        return "redirect:/admin/tags/all-tags"
        }

    @GetMapping("/delete/{id}")
    fun deleteTag(@PathVariable("id") id: Long): String {
        tagService.delTag(id)
        return "redirect:/admin/tags/all-tags"
    }

}