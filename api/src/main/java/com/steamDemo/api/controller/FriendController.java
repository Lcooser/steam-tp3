package com.steamDemo.api.controller;

import com.steamDemo.api.domain.Friend.Friend;
import com.steamDemo.api.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping
    public ResponseEntity<Friend> addFriend(@RequestParam UUID userId, @RequestParam UUID friendUserId,
                                            @RequestParam String friendName) {
        Friend friend = friendService.addFriend(userId, friendUserId, friendName);
        return new ResponseEntity<>(friend, HttpStatus.CREATED);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable UUID friendId) {
        friendService.removeFriend(friendId);
        return ResponseEntity.noContent().build();
    }
}
