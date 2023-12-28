package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.Domain.User;
import com.example.UserAuthenticationService.Exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.Exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exception.UserNotFoundException;
import com.example.UserAuthenticationService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws InvalidCredentialsException {
        if (userRepository.findById(email).isEmpty()) {
            throw new InvalidCredentialsException();
        } else {
            return userRepository.findByEmailAndPassword(email, password);
        }
    }

    @Override
    public User updateUser(String email, User user) throws UserAlreadyExistsException {
        if (userRepository.findById(email).isEmpty()) {
            throw new UserAlreadyExistsException();
        }
        User us = userRepository.findById(email).get();
        if (us.getEmail().equals(email)) {
            us.setPassword(user.getPassword());
        }
        return userRepository.save(us);
    }

    @Override
    public Boolean deleteUser(String email) throws UserNotFoundException {
        boolean value = false;
        if (userRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            userRepository.deleteById(email);
            value = true;
        }
        return value;
    }

    @Override
    public List<User> getAllVerifiedUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<String> getUserEmail() {
        List<User> us = userRepository.findAll();
        List<String> list = new ArrayList<>();
        for (User u : us) {
            list.add(u.getEmail());
        }
        return list;
    }
}