package kz.webapp.routine.repository

import kz.webapp.routine.model.entity.TagEntity
import kz.webapp.routine.model.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface TagRepo: JpaRepository<TagEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT tag_name FROM tags;" )
    fun findAllTags(): List<String>

    fun findByTagName(tagName: String): TagEntity
}