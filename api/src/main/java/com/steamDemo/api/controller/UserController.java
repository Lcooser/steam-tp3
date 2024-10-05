package com.steamDemo.api.controller;

import com.steamDemo.api.domain.User.User;
import com.steamDemo.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String userName, @RequestParam String password,
                                           @RequestParam String email, @RequestParam String gender,
                                           @RequestParam String country, @RequestParam String city,
                                           @RequestParam String address, @RequestParam String phone,
                                           @RequestParam String birthDate, @RequestParam String emailRecover) throws ParseException {
        Date parsedBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        User user = userService.createUser(userName, password, email, gender, country, city, address, phone, parsedBirthDate, emailRecover);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody User newUserDetails) {
        User user = userService.updateUser(userId, newUserDetails);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
