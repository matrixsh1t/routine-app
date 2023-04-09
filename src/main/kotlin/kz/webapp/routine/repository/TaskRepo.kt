package kz.webapp.routine.repository

import kz.webapp.routine.model.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepo: JpaRepository<TaskEntity, Int> {
//    fun findByTaskId(taskId: Int): TasksEntity
//    @Query(nativeQuery = true, value = "select * from tasks where perform_date <= current_date order by task")

    @Query("SELECT te FROM TaskEntity te WHERE te.status = 'a' AND te.performDate <= CURRENT_DATE ORDER BY te.performDate")
    fun findAllTodaysTasks(): List<TaskEntity>

    @Query("SELECT te FROM TaskEntity te ORDER BY te.createDate")
    fun findAllTasks(): List<TaskEntity>

    //get all tasks for tomorrow depending on weekday
    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE perform_date =\n" +
            "    CASE\n" +
            "        WHEN extract(dow from current_date) = 5 THEN current_date + INTERVAL '3 days'\n" +
            "        WHEN extract(dow from current_date) = 6 THEN current_date + INTERVAL '2 days'\n" +
            "        ELSE current_date + INTERVAL '1 day'\n" +
            "    END;")
    fun findAllTomorrowsTasks(): List<TaskEntity>

    //get all tasks for next week
    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM tasks\n" +
            "WHERE perform_date >= DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '1 week' -- начало следующей недели\n" +
            "  AND perform_date <= DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '2 week - 2 days' -- конец следующей недели (пятница)\n" +
            "  AND EXTRACT('dow' FROM perform_date) BETWEEN 1 AND 5 -- день недели от понедельника (1) до пятницы (5)\n")
    fun findAllNextWeeksTasks(): List<TaskEntity>

    //get all tasks for next month
    @Query(nativeQuery = true, value = "SELECT * FROM tasks \n" +
            "WHERE perform_date >= DATE_TRUNC('month', CURRENT_DATE + INTERVAL '1 month') \n" +
            "AND perform_date < DATE_TRUNC('month', CURRENT_DATE + INTERVAL '2 month') \n" +
            "AND extract(day from perform_date) >= 1 \n" +
            "AND extract(day from perform_date) <= extract(day from DATE_TRUNC('month', CURRENT_DATE + INTERVAL '2 month') - INTERVAL '1 day');\n")
    fun findAllNextMonthsTasks(): List<TaskEntity>

    @Query("SELECT te FROM TaskEntity te WHERE te.status = 'a' ORDER BY te.performDate")
    fun findAllActiveTasks(): List<TaskEntity>
    override fun findAll(): List<TaskEntity>
}