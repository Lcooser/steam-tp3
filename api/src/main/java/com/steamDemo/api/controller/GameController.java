package com.steamDemo.api.controller;

import com.steamDemo.api.domain.Game.Game;
import com.steamDemo.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestParam UUID userId, @RequestParam String title,
                                           @RequestParam String description, @RequestParam BigDecimal price,
                                           @RequestParam int minimumAge) {
        Game game = gameService.createGame(userId, title, description, price, minimumAge);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Game> updateGame(@PathVariable UUID gameId, @RequestParam String newTitle,
                                           @RequestParam String newDescription, @RequestParam BigDecimal newPrice,
                                           @RequestParam int newMinimumAge) {
        Game game = gameService.updateGame(gameId, newTitle, newDescription, newPrice, newMinimumAge);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
