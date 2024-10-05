package com.steamDemo.api.domain.Comment;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Game.Game;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "comments")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    private String content;
    private int dislikes;
    private int likes;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "replies_id")
    private Comment comment;

    public static Comment createComment(Account account, Game game, String content, Comment parentComment) {
        return new Comment(UUID.randomUUID(), content, 0, 0, LocalDateTime.now(), account, game, parentComment);
    }
}
