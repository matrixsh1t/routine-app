package kz.webapp.routine.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "tags")
@DynamicUpdate

class TagEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
    @SequenceGenerator(name = "tag_generator", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    @Column(name = "tag_id")
    val tagId: Int? = null,

    @Column(name = "tag_name", unique = true)
    val tagName: String
)