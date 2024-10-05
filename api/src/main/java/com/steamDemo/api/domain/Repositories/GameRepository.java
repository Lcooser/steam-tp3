package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
