package com.xyz.shortener.controller;

import com.xyz.shortener.dto.AuthRequest;
import com.xyz.shortener.dto.AuthResponse;
import com.xyz.shortener.dto.MessageResponse;
import com.xyz.shortener.security.JWTUtil;
import com.xyz.shortener.repository.UserRepo;
import com.xyz.shortener.entity.User;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepo userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        validateAuthentication(authRequest.getUserName(), authRequest.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        String token = jwtUtil.generateToken(authRequest.getUserName());
        return ResponseEntity.ok(new AuthResponse(authRequest.getUserName(),token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest authRequest) {
        if (userRepository.findByUserName(authRequest.getUserName()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(authRequest.getUserName(),
                encoder.encode(authRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void validateAuthentication(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        }catch(BadCredentialsException ex) {
            throw new Exception("Check Username / password again ! ", ex);
        }
    }

}
