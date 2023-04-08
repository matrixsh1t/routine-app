package kz.webapp.routine.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate

@Entity
@Table(name = "tasks")
@DynamicUpdate
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", sequenceName = "tasks_task_id_seq", allocationSize = 1)
    @Column(name = "task_id")
    val taskId: Int,

    @NotEmpty
    @Column(name = "task")
    val task: String,

    @NotEmpty
    @Column(name = "comment")
    val comment: String?,

    @Column(name = "perform_date")
    val performDate: LocalDate,

    @Column(name = "date_create")
    val createDate: LocalDate,

    @NotEmpty
    @Column(name = "status")
    val status: String,
    )