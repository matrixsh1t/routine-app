package kz.webapp.routine.service

import kz.webapp.routine.model.dto.AddTagDto
import kz.webapp.routine.model.entity.TagEntity

interface TagService {
    fun addTag(tagDto: AddTagDto)

    fun delTag(id: Long)

    fun showTags(): List<TagEntity>

}