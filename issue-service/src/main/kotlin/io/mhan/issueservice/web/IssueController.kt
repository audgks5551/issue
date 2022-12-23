package io.mhan.issueservice.web

import io.mhan.issueservice.config.AuthUser
import io.mhan.issueservice.domain.enums.IssueStatus
import io.mhan.issueservice.model.IssueRequest
import io.mhan.issueservice.service.IssueService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest
    ) = issueService.create(authUser.userId, request)

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam(required = false, defaultValue = "TODO") status : IssueStatus,
    ) = issueService.getAll(status)

    @GetMapping("/{id}")
    fun get(
        authUser: AuthUser,
        @PathVariable id: Long,
    ) = issueService.get(id)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: IssueRequest
    ) = issueService.edit(authUser.userId, id, request)
}
