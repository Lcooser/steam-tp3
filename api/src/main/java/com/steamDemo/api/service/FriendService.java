package com.steamDemo.api.service;

import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.Friend.Friend;
import com.steamDemo.api.domain.Repositories.FriendRepository;
import com.steamDemo.api.domain.User.User;
import com.steamDemo.api.domain.Repositories.AuditRepository;
import com.steamDemo.api.domain.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public Friend addFriend(UUID userId, UUID friendUserId, String friendName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friendUser = userRepository.findById(friendUserId).orElseThrow(() -> new IllegalArgumentException("Friend user not found"));

        // Verifica se a amizade já existe
        if (friendRepository.existsByUserAndFriendUser(user, friendUser)) {
            throw new IllegalArgumentException("Friendship already exists");
        }

        // Cria e salva a nova amizade
        Friend friend = Friend.addFriend(user, friendUser, friendName);
        Friend savedFriend = friendRepository.save(friend);

        // Log de auditoria
        logAudit("Friend", savedFriend.getId(), "CREATE", null, savedFriend.toString());
        return savedFriend;
    }

    @Transactional
    public void removeFriend(UUID friendId) {
        Friend existingFriend = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        // Remove a amizade
        friendRepository.delete(existingFriend);

        // Log de auditoria
        logAudit("Friend", existingFriend.getId(), "DELETE", existingFriend.toString(), null);
    }

    private void logAudit(String entityName, UUID entityId, String operation, String oldValue, String newValue) {
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setOperation(Audit.Operation.valueOf(operation));
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy("system"); // Ou o usuário autenticado, se disponível
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        auditRepository.save(audit);
    }
}
