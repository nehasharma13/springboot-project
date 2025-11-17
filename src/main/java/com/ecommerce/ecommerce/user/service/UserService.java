package com.ecommerce.ecommerce.user.service;

import com.ecommerce.ecommerce.common.exceptions.InvalidCredentialsException;
import com.ecommerce.ecommerce.common.exceptions.UserAlreadyExistsException;
import com.ecommerce.ecommerce.security.JwtUtil;
import com.ecommerce.ecommerce.user.entity.User;
import com.ecommerce.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    public User registerUser(User user) {
        //check if email already exists
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");

        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);

    }
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Email not found"));
        if(passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        } else {
            throw new InvalidCredentialsException("Invalid password");
        }
    }


}
