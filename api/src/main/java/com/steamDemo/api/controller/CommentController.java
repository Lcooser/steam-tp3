package com.steamDemo.api.controller;

import com.steamDemo.api.domain.Comment.Comment;
import com.steamDemo.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestParam UUID accountId, @RequestParam UUID gameId,
                                                 @RequestParam String content, @RequestParam(required = false) UUID parentCommentId) {
        Comment comment = commentService.createComment(accountId, gameId, content, parentCommentId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable UUID commentId, @RequestParam String newContent) {
        Comment comment = commentService.updateComment(commentId, newContent);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
