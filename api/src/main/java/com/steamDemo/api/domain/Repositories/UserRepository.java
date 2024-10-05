package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
