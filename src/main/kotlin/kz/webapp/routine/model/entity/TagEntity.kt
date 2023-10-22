package kz.webapp.routine.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "tags")
@DynamicUpdate

class TagEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tag_generator")
    @SequenceGenerator(name = "tag_generator", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    @Column(name = "tag_id")
    val tagId: Long,

    @Column(name = "tag_name")
    val tagName: String,

    @ManyToMany(mappedBy = "tags")
//    @JoinTable(
//    name = "task_tags",
//    joinColumns = [JoinColumn(name = "tag_id")],
//    inverseJoinColumns = [JoinColumn(name = "task_id")])
    var tasks: Set<TaskEntity> = HashSet()
)
