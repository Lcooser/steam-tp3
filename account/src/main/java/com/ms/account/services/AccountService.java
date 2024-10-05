package com.ms.account.services;

import com.ms.account.dtos.UserDTO;
import com.ms.account.models.Account;
import com.ms.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account createAccount(UserDTO request) {
        Account account = Account.createAccount(request.getUserName(), request.getCountry(), request.getEmail());
        return accountRepository.save(account);
    }
}
