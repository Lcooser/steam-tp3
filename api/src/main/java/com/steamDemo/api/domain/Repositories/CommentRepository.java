package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
