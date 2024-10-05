package com.steamDemo.api.service;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.Comment.Comment;
import com.steamDemo.api.domain.Game.Game;
import com.steamDemo.api.domain.Repositories.AuditRepository;
import com.steamDemo.api.domain.Repositories.AccountRepository;
import com.steamDemo.api.domain.Repositories.CommentRepository;
import com.steamDemo.api.domain.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public Comment createComment(UUID accountId, UUID gameId, String content, UUID parentCommentId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game not found"));
        Comment parentComment = parentCommentId != null ? commentRepository.findById(parentCommentId).orElse(null) : null;

        Comment comment = Comment.createComment(account, game, content, parentComment);
        Comment savedComment = commentRepository.save(comment);
        logAudit("Comment", savedComment.getId(), "CREATE", null, savedComment.toString());
        return savedComment;
    }

    @Transactional
    public Comment updateComment(UUID commentId, String newContent) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        String oldValue = existingComment.toString();
        existingComment.setContent(newContent);
        Comment updatedComment = commentRepository.save(existingComment);
        logAudit("Comment", updatedComment.getId(), "UPDATE", oldValue, updatedComment.toString());
        return updatedComment;
    }

    @Transactional
    public void deleteComment(UUID commentId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        commentRepository.delete(existingComment);
        logAudit("Comment", existingComment.getId(), "DELETE", existingComment.toString(), null);
    }

    private void logAudit(String entityName, UUID entityId, String operation, String oldValue, String newValue) {
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setOperation(Audit.Operation.valueOf(operation));
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy("system"); // Ou o usuário autenticado
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        auditRepository.save(audit);
    }
}
