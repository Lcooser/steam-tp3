package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Friend.Friend;
import com.steamDemo.api.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    boolean existsByUserAndFriendUser(User user, User friendUser);
}
