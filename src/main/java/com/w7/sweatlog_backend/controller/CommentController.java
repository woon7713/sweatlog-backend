package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.CommentRequest;
import com.w7.sweatlog_backend.dto.CommentResponse;
import com.w7.sweatlog_backend.dto.PostResponse;
import com.w7.sweatlog_backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequest request) {

        CommentResponse response = commentService.createComment(postId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Page<CommentResponse>> getComment(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> response = commentService.getComments(postId, pageable);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
