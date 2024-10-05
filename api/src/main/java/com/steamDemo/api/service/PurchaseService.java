package com.steamDemo.api.service;

import com.steamDemo.api.domain.Purchase.Purchase;
import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Game.Game;
import com.steamDemo.api.domain.Repositories.AuditRepository;
import com.steamDemo.api.domain.Transaction.Transaction;
import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.Repositories.PurchaseRepository;
import com.steamDemo.api.domain.Repositories.TransactionRepository;
import com.steamDemo.api.domain.Repositories.AccountRepository;
import com.steamDemo.api.domain.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public Purchase createPurchase(UUID accountId, UUID gameId, UUID transactionId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        Purchase purchase = Purchase.createPurchase(account, game, transaction);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        logAudit("Purchase", savedPurchase.getId(), "CREATE", null, savedPurchase.toString());
        return savedPurchase;
    }

    @Transactional
    public void deletePurchase(UUID purchaseId) {
        Purchase existingPurchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));
        purchaseRepository.delete(existingPurchase);
        logAudit("Purchase", existingPurchase.getId(), "DELETE", existingPurchase.toString(), null);
    }

    private void logAudit(String entityName, UUID entityId, String operation, String oldValue, String newValue) {
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setOperation(Audit.Operation.valueOf(operation));
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy("system"); // Ou o usuário autenticado
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        auditRepository.save(audit);
    }
}
