package io.mhan.issueservice.service

import io.mhan.issueservice.domain.Issue
import io.mhan.issueservice.domain.IssueRepository
import io.mhan.issueservice.domain.enums.IssueStatus
import io.mhan.issueservice.model.IssueRequest
import io.mhan.issueservice.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus) : List<IssueResponse>? =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse(it) }
}