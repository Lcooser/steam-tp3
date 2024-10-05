package com.steamDemo.api.domain.Friend;

import com.steamDemo.api.domain.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "friends")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "friend_name")
    private String friendName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_user_id")
    private User friendUser;

    public static Friend addFriend(User user, User friendUser, String friendName) {
        return new Friend(UUID.randomUUID(), friendName, user, friendUser);
    }
}
