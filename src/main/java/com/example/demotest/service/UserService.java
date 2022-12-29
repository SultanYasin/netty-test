package com.example.demotest.service;

import com.example.demotest.models.User;

import com.example.demotest.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getUserById(UUID id);

    User createUser(User user);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);

}

