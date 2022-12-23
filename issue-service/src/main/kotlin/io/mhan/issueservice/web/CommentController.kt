package io.mhan.issueservice.web

import io.mhan.issueservice.config.AuthUser
import io.mhan.issueservice.model.CommentRequest
import io.mhan.issueservice.model.CommentResponse
import io.mhan.issueservice.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest
    ) : CommentResponse = commentService.create(issueId, authUser.userId, authUser.username, request)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: CommentRequest
    ) : CommentResponse? = commentService.edit(id, authUser.userId, request)
}