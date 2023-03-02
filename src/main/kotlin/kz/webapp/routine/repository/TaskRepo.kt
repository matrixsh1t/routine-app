package kz.webapp.routine.repository

import kz.webapp.routine.model.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepo: JpaRepository<TaskEntity, Int> {
//    fun findByTaskId(taskId: Int): TasksEntity
//    @Query(nativeQuery = true, value = "select * from tasks")
//    fun findAllTasks(): List<TaskEntity>

    override fun findAll(): List<TaskEntity>
}