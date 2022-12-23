package io.mhan.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByIdAndUserId(id: Long, userId: Long) : Comment?
    fun findByIssueId(issueId: Long) : List<Comment>?
}