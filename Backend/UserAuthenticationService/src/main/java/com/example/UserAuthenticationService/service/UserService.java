package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.Domain.User;
import com.example.UserAuthenticationService.Exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.Exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User saveUser(User user) throws UserAlreadyExistsException;

    User getUserByEmailAndPassword(String email, String password) throws InvalidCredentialsException;

    User updateUser(String email, User user) throws UserAlreadyExistsException;

    Boolean deleteUser(String email) throws UserNotFoundException;

    List<User> getAllVerifiedUsers();

    List<String> getUserEmail();
}