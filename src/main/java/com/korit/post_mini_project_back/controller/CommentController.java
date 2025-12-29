package com.korit.post_mini_project_back.controller;

import com.korit.post_mini_project_back.dto.request.CreatePostCommentReqDto;
import com.korit.post_mini_project_back.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> createComments(@PathVariable int postId, @RequestBody CreatePostCommentReqDto dto) {
        commentService.createComment(postId, dto);
        return ResponseEntity.ok("댓글 작성 완료");
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable int postId){
        return ResponseEntity.ok(commentService.getComments(postId));
    }
}