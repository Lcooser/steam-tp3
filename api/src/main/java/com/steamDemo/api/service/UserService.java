package com.steamDemo.api.service;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.User.User;
import com.steamDemo.api.domain.Repositories.UserRepository;
import com.steamDemo.api.domain.Repositories.AuditRepository;
import com.steamDemo.api.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String ACCOUNT_SERVICE_URL = "http://localhost:8083/api/accounts";

    @Transactional
    public User createUser(String userName, String password, String email, String gender, String country, String city, String address, String phone, Date birthDate, String emailRecover) {
        User user = User.createUser(userName, password, email, gender, country, city, address, phone, birthDate, emailRecover);
        User savedUser = userRepository.save(user);

        AccountDTO accountRequest = new AccountDTO(savedUser.getId(), savedUser.getUserName(), savedUser.getCountry(), savedUser.getEmail(), "Conta Padrão");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AccountDTO> requestEntity = new HttpEntity<>(accountRequest, headers);

        ResponseEntity<Account> response = restTemplate.postForEntity(ACCOUNT_SERVICE_URL, requestEntity, Account.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Falha ao criar a conta");
        }

        logAudit("User", savedUser.getId(), "CREATE", null, savedUser.toString());
        return savedUser;
    }

    @Transactional
    public User updateUser(UUID userId, User newUserDetails) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String oldValue = existingUser.toString();
        existingUser.setUserName(newUserDetails.getUserName());
        existingUser.setPassword(newUserDetails.getPassword());
        existingUser.setEmail(newUserDetails.getEmail());
        existingUser.setBirthDate(newUserDetails.getBirthDate());
        existingUser.setGender(newUserDetails.getGender());
        existingUser.setCountry(newUserDetails.getCountry());
        existingUser.setCity(newUserDetails.getCity());
        existingUser.setAddress(newUserDetails.getAddress());
        existingUser.setPhone(newUserDetails.getPhone());
        existingUser.setEmailRecover(newUserDetails.getEmailRecover());
        User updatedUser = userRepository.save(existingUser);
        logAudit("User", updatedUser.getId(), "UPDATE", oldValue, updatedUser.toString());
        return updatedUser;
    }

    @Transactional
    public void deleteUser(UUID userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(existingUser);
        logAudit("User", existingUser.getId(), "DELETE", existingUser.toString(), null);
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
