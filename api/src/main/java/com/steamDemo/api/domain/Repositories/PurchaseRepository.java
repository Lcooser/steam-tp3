package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
