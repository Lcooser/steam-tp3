package com.steamDemo.api.service;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.User.User;
import com.steamDemo.api.domain.Repositories.AccountRepository;
import com.steamDemo.api.domain.Repositories.AuditRepository;
import com.steamDemo.api.domain.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public Account createAccount(UUID userId, double initialBalance) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Account account = Account.createAccount(user, initialBalance);
        Account savedAccount = accountRepository.save(account);
        logAudit("Account", savedAccount.getId(), "CREATE", null, savedAccount.toString());
        return savedAccount;
    }

    @Transactional
    public Account updateAccount(UUID accountId, double newBalance) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        String oldValue = existingAccount.toString();
        existingAccount.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(existingAccount);
        logAudit("Account", updatedAccount.getId(), "UPDATE", oldValue, updatedAccount.toString());
        return updatedAccount;
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        accountRepository.delete(existingAccount);
        logAudit("Account", existingAccount.getId(), "DELETE", existingAccount.toString(), null);
    }

    private void logAudit(String entityName, UUID entityId, String operation, String oldValue, String newValue) {
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setOperation(Audit.Operation.valueOf(operation));
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy("system");
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        auditRepository.save(audit);
    }
}
