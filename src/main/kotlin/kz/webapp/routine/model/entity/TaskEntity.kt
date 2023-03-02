package kz.webapp.routine.model.entity


import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime


@Entity
@Table(name = "tasks")
@DynamicUpdate
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", sequenceName = "tasks_task_id_seq", allocationSize = 1)
    @Column(name = "task_id")
    val taskId: Int?,

    @Column(name = "task")
    val task: String,

    @Column(name = "comment")
    val comment: String,

    @Column(name = "status")
    val status: String,

    @Column(name = "week")
    val week: Int,

    @Column(name = "city")
    val city: String,

    @Column(name = "date_create")
    val dateCreate: LocalDateTime,

    @Column(name = "date_due", nullable = true)
    val dateDue: LocalDateTime?,

    @Column(name = "date_close", nullable = true)
    val dateClose: LocalDateTime?,
)