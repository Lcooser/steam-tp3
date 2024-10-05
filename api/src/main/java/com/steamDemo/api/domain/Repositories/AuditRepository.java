package com.steamDemo.api.domain.Repositories;

import com.steamDemo.api.domain.Audit.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<Audit, UUID> {
}
