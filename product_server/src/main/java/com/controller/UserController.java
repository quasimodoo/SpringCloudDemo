package com.controller;

import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserDao userDao;
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userDao.findById(id);
    }
}
