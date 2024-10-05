package com.steamDemo.api.service;

import com.steamDemo.api.domain.Audit.Audit;

import com.steamDemo.api.domain.Repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    private void logAudit(String entityName, UUID entityId, Audit.Operation operation, String oldValue, String newValue) {
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setOperation(operation);
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy("system");
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        auditRepository.save(audit);
    }


}