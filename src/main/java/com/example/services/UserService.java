package com.example.services;

import com.example.exceptions.InvalidTokenException;
import com.example.exceptions.PasswordNotMatchingException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.Token;
import com.example.models.User;
import com.example.repositories.TokenRepository;
import com.example.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenRepository tokenRepository;


    // This method is used to register a new user
    public User signUp(String name, String email, String password) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User with Email Id " + email + " already exists");
        }
        // Create a new user object and save it to the database
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // Hash the password before saving it to the database using BCryptPasswordEncoder
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(true);
        return userRepository.save(user);
    }


    //  This method is used to log in a user
    public Token logIn(String email, String password) throws UserNotFoundException, PasswordNotMatchingException {
        // Find the user with the given email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with Email Id " + email + " is not registered");
        }
        User user = optionalUser.get();

        // Check if the password is correct
        if(bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            // Check if the user already has a token
            Optional<Token> optionalToken = tokenRepository.findByUserAndIsDeleted(user, false);
            if(optionalToken.isPresent()) {
                return optionalToken.get();
            }

            // Create a new token for the user if the user does not have a token and save it to the DB
            Token token = new Token();
            // Generate a random alphanumeric string of length 128
            token.setValue(RandomStringUtils.randomAlphanumeric(128));
            // Set the expiry date of the token to 30 days from the current date
            Date expiryAt = new Date();
            expiryAt.setDate(expiryAt.getDate() + 30);
            token.setExpiryAt(expiryAt);
            token.setUser(user);
            return tokenRepository.save(token);
        }

        throw new PasswordNotMatchingException("Password is Incorrect");
    }


    // This method is used to log out a user
    public void logOut(String userToken) throws InvalidTokenException {
        // Find the token with the given value and is not deleted
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeleted(userToken, false);
        if(optionalToken.isEmpty()) {
            throw new InvalidTokenException("Token is Invalid");
        }
        // If the token is found, set the token as deleted in the DB
        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }


    // This method is used to validate a token
    public User validateToken(String userToken) throws InvalidTokenException {
        // Find the token with the given value, is not deleted and has not expired
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeletedAndExpiryAtGreaterThan(userToken, false, new Date());
        if(optionalToken.isEmpty()) {
            throw new InvalidTokenException("Token is Invalid");
        }
        // If the token is found, return the user associated with the token
        return optionalToken.get().getUser();
    }

}
