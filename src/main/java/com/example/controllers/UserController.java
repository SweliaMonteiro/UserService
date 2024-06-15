package com.example.controllers;

import com.example.dtos.*;
import com.example.exceptions.InvalidTokenException;
import com.example.exceptions.PasswordNotMatchingException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.Token;
import com.example.models.User;
import com.example.userservice.dtos.*;
import com.example.userservice.exceptions.*;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // This method is used to sign up a user and return the user details after saving it to the database
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return UserDto.from(user);
    }


    // This method is used to log in a user and return a token for the user to access the application resources
    @PostMapping("/login")
    public TokenDto logIn(@RequestBody LogInRequestDto logInRequestDto) throws UserNotFoundException, PasswordNotMatchingException {
        Token token = userService.logIn(logInRequestDto.getEmail(), logInRequestDto.getPassword());
        return TokenDto.from(token);
    }


    // This method is used to log out a user by deleting the token from the database
    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogOutRequestDto logOutRequestDto) throws InvalidTokenException {
        userService.logOut(logOutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // This method is used to validate the token and return the user details
    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateToken(@PathVariable("token") String token) {
        try {
            User user = userService.validateToken(token);
            return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
        }
        catch (InvalidTokenException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
