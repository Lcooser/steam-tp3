package com.steamDemo.api.domain.User;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Friend.Friend;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Table(name = "users")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String userName;
    private String password;
    private String email;
    private Date birthDate;
    private String gender;
    private String country;
    private String city;
    private String address;
    private String phone;
    private String emailRecover;

    @OneToOne(mappedBy = "user")
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends = new ArrayList<>();

    public User(String userName, String password, String email, Date birthDate, String gender, String country, String city, String address, String phone, String emailRecover) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.emailRecover = emailRecover;
    }
    public static User createUser(String userName, String password, String email, String gender, String country, String city, String address, String phone, Date birthDate, String emailRecover) {
        validateUserName(userName);
        validatePassword(password);
        validateEmail(email);
        validateEmail(emailRecover);
        validateBirthDate(birthDate);
        validatePhone(phone);
        validateGender(gender);

        return new User(userName, password, email, birthDate, gender, country, city, address, phone, emailRecover);
    }

    private static void validateUserName(String userName) {
        if (userName == null || userName.length() < 5) {
            throw new IllegalArgumentException("Username must contain at least 5 characters");
        }
    }

    private static void validatePassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,32}$");
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) {
            System.out.println("Password valid");
        } else {
            throw new IllegalArgumentException("Password must contain at least 8 characters, including an uppercase letter, a lowercase letter, a number, and a special character");
        }
    }


    public static void validateEmail(String email) {
        Pattern emailPattern = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        );
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }

    public static void validateBirthDate(Date birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }

        LocalDate birthDateLocal = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDateLocal, today);

        if (age.getYears() < 15) {
            throw new IllegalArgumentException("Age must be greater than 15 years");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || phone.length() > 15 || phone.length() < 13 ) {
            throw new IllegalArgumentException("Invalid Phone Number");
        }
    }

    public static void validateGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
    }
}
