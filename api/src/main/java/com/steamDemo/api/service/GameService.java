package com.steamDemo.api.service;

import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.Game.Game;
import com.steamDemo.api.domain.Repositories.UserRepository;
import com.steamDemo.api.domain.User.User;
import com.steamDemo.api.domain.Repositories.GameRepository;
import com.steamDemo.api.domain.Repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public Game createGame(UUID userId, String title, String description, BigDecimal price, int minimumAge) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Game game = Game.createGame(title, description, price, minimumAge, user);
        Game savedGame = gameRepository.save(game);
        logAudit("Game", savedGame.getId(), "CREATE", null, savedGame.toString());
        return savedGame;
    }

    @Transactional
    public Game updateGame(UUID gameId, String newTitle, String newDescription, BigDecimal newPrice, int newMinimumAge) {
        Game existingGame = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        String oldValue = existingGame.toString();
        existingGame.setGameTitle(newTitle);
        existingGame.setGameDescription(newDescription);
        existingGame.setGamePrice(newPrice);
        existingGame.setMinimumAge(newMinimumAge);
        Game updatedGame = gameRepository.save(existingGame);
        logAudit("Game", updatedGame.getId(), "UPDATE", oldValue, updatedGame.toString());
        return updatedGame;
    }

    @Transactional
    public void deleteGame(UUID gameId) {
        Game existingGame = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        gameRepository.delete(existingGame);
        logAudit("Game", existingGame.getId(), "DELETE", existingGame.toString(), null);
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
