package com.steamDemo.api.domain.Account;


import com.steamDemo.api.domain.Friend.Friend;
import com.steamDemo.api.domain.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    private String accountDescription;
    private String creditCardNumber;
    private Date cardExpirationDate;
    private String cardCvv;
    private String accountStatus;
    private double balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Account createAccount(User user, double Balance) {
        validateUser(user);
        Account account = new Account();
        account.setUser(user);
        account.balance = 0;
        return account;
    }

    private static void validateUser(User user) {
        if (user.getUserName() == null || user.getAddress() == null || user.getBirthDate() == null ||
                user.getCity() == null || user.getGender() == null || user.getPhone() == null ||
                user.getEmail() == null || user.getEmailRecover() == null || user.getCountry() == null) {
            throw new IllegalArgumentException("There are fields in user data that are not complete.");
        }
    }
}
