package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
