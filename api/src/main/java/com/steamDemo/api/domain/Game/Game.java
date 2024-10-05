package com.steamDemo.api.domain.Game;

import com.steamDemo.api.domain.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    private UUID id;

    private String gameTitle;
    private String gameDescription;
    private BigDecimal gamePrice;
    private int minimumAge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Game createGame(String gameTitle, String gameDescription, BigDecimal gamePrice, int minimumAge, User user) {
        validateGameTitle(gameTitle);
        validateGameDescription(gameDescription);
        validateGameMinimumAge(minimumAge, user);

        Game game = new Game();
        game.setGameTitle(gameTitle);
        game.setGameDescription(gameDescription);
        game.setGamePrice(gamePrice);
        game.setMinimumAge(minimumAge);
        game.setUser(user);
        return game;
    }

    private static void validateGameTitle(String gameTitle) {
        if (gameTitle == null || gameTitle.length() < 3) {
            throw new IllegalArgumentException("Game title must be at least 3 characters long.");
        }
        if (!gameTitle.matches("[a-zA-Z0-9\\s]+")) {
            throw new IllegalArgumentException("Game title should not contain special characters.");
        }
    }

    private static void validateGameDescription(String gameDescription) {
        if (gameDescription == null || gameDescription.length() < 15) {
            throw new IllegalArgumentException("Game description must be at least 15 characters long.");
        }
        if (!gameDescription.matches("[a-zA-Z0-9\\s]+")) {
            throw new IllegalArgumentException("Game description should not contain special characters.");
        }
    }

    private static void validateGameMinimumAge(int minimumAge, User user) {
        if (user == null || user.getBirthDate() == null) {
            throw new IllegalArgumentException("User or birth date cannot be null.");
        }

        Date birthDate = user.getBirthDate();
        LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period agePeriod = Period.between(birthDateLocal, today);
        int age = agePeriod.getYears();

        if (age < minimumAge) {
            throw new IllegalArgumentException("User is not old enough. Minimum age required is " + minimumAge);
        }
    }
}
