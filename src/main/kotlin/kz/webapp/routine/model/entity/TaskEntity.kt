package kz.webapp.routine.model.entity


import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import kz.webapp.routine.model.enums.City
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

    @Column(name = "comment")
    val comment: String?,

    @Column(name = "city")
    @Enumerated(EnumType.STRING)
    val city: City,

    @NotEmpty
    @Column(name = "status")
    val status: String,

    @Column(name = "date_create")
    val createDate: LocalDate,

    @Column(name = "date_due")
    val dueDate: LocalDate?,

    @Column(name = "date_close")
    val closeDate: LocalDate?,

    @ManyToOne
    @JoinColumn(name = "account_id", nullable=false)
    val account_id: AccountEntity,
    )