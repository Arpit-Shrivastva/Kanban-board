package com.example.UserAuthenticationService.Controller;

import com.example.UserAuthenticationService.Domain.User;
import com.example.UserAuthenticationService.Exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.Exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exception.UserNotFoundException;
import com.example.UserAuthenticationService.Security.SecurityTokenGenerator;
import com.example.UserAuthenticationService.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserServiceImpl userService;
    private final SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserServiceImpl userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/saveuser")
    public ResponseEntity saveUsers(@RequestBody User user) throws UserAlreadyExistsException {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody User user) throws InvalidCredentialsException {
        if (userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword()) == null) {
            throw new InvalidCredentialsException();
        }
        String token = securityTokenGenerator.createToken(user);
        return new ResponseEntity(token, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(String email, @RequestBody User user) throws UserAlreadyExistsException {
        return new ResponseEntity<>(userService.updateUser(email, user), HttpStatus.CREATED);
    }

    @GetMapping("/getallusers")
    public ResponseEntity getUsers() {
        return new ResponseEntity<>(userService.getAllVerifiedUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity deleteUser(String email) throws UserNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus.OK);
    }

    @GetMapping("/getuseremail")
    public ResponseEntity getUserEmail() {
        return new ResponseEntity<>(userService.getUserEmail(), HttpStatus.OK);
    }

}
