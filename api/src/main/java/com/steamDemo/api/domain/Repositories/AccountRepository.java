package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
