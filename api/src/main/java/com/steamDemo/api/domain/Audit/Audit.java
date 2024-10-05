package com.steamDemo.api.domain.Audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @Id
    @GeneratedValue
    private UUID id;

    private String entityName;
    private UUID entityId;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    private LocalDateTime timestamp;
    private String changedBy;
    private String oldValue;
    private String newValue;

    public Audit(String entityName, UUID entityId, Operation operation, LocalDateTime timestamp, String changedBy, String oldValue, String newValue) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.operation = operation;
        this.timestamp = timestamp;
        this.changedBy = changedBy;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public enum Operation {
        CREATE,
        UPDATE,
        DELETE,
        UNKNOWN
    }
}
