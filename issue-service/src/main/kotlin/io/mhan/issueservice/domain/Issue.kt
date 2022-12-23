package io.mhan.issueservice.domain

import io.mhan.issueservice.domain.enums.IssuePriority
import io.mhan.issueservice.domain.enums.IssueStatus
import io.mhan.issueservice.domain.enums.IssueType
import jakarta.persistence.*

@Entity
@Table
class Issue (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var userId: Long,

    @OneToMany(mappedBy = "issue", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @Column
    var summary: String,

    @Column
    var description: String,

    @Column
    @Enumerated(EnumType.STRING)
    var type: IssueType,

    @Column
    @Enumerated(EnumType.STRING)
    var priority: IssuePriority,

    @Column
    @Enumerated(EnumType.STRING)
    var status: IssueStatus,
    ) : BaseEntity()