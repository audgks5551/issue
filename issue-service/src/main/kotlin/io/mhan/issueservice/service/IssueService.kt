package io.mhan.issueservice.service

import io.mhan.issueservice.domain.Issue
import io.mhan.issueservice.domain.IssueRepository
import io.mhan.issueservice.model.IssueRequest
import io.mhan.issueservice.model.IssueResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class IssueService(
    private val issueRepository: IssueRepository
) {

    @Transactional
    fun create(userId: Long, request: IssueRequest) : IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issueRepository.save(issue))
    }
}