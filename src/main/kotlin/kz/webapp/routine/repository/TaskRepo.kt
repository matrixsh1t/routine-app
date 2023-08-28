package kz.webapp.routine.repository

import kz.webapp.routine.model.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TaskRepo: JpaRepository<TaskEntity, Int> {
    @Query("SELECT te FROM TaskEntity te WHERE te.status = 'a' AND te.dueDate <= CURRENT_DATE") //ORDER BY te.responsible
    fun findAllTodaysTasks(): List<TaskEntity>

    @Query("SELECT te FROM TaskEntity te ORDER BY te.createDate")
    fun findAllTasks(): List<TaskEntity>

    //get all tasks for tomorrow depending on weekday
    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE date_due =\n" +
            "    CASE\n" +
            "        WHEN extract(dow from current_date) = 5 THEN current_date + INTERVAL '3 days'\n" +
            "        WHEN extract(dow from current_date) = 6 THEN current_date + INTERVAL '2 days'\n" +
            "        ELSE current_date + INTERVAL '1 day'\n" +
            "    END ") //ORDER BY responsible;
    fun findAllTomorrowsTasks(): List<TaskEntity>

    //get all tasks for next week
    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM tasks\n" +
            "WHERE date_due >= DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '1 week' -- начало следующей недели\n" +
            "  AND date_due <= DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '2 week - 2 days' -- конец следующей недели (пятница)\n" +
            "  AND EXTRACT('dow' FROM date_due) BETWEEN 1 AND 5") //ORDER BY responsible
    fun findAllNextWeeksTasks(): List<TaskEntity>

    //get all tasks for next month
    @Query(nativeQuery = true, value = "SELECT * FROM tasks \n" +
            "WHERE date_due >= DATE_TRUNC('month', CURRENT_DATE + INTERVAL '1 month') \n" +
            "AND date_due < DATE_TRUNC('month', CURRENT_DATE + INTERVAL '2 month') \n" +
            "AND extract(day from date_due) >= 1 \n" +
            "AND extract(day from date_due) <= extract(day from DATE_TRUNC('month', CURRENT_DATE + INTERVAL '2 month') - INTERVAL '1 day') ") //ORDER BY responsible;
    fun findAllNextMonthsTasks(): List<TaskEntity>

    //get all tasks of current account
    fun findAllByAccountIdUsernameOrderByDueDate(username: String): List<TaskEntity>
}