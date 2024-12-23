package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.configuration.JwtProvider;
import com.ankit.socialmedia.exception.UserException;
import com.ankit.socialmedia.request.LoginRequest;
import com.ankit.socialmedia.response.AuthResponse;
import com.ankit.socialmedia.service.implementation.UserDetailService;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private UserServiceImp userServiceImp;
    @Resource
    private UserDetailService userDetailService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) {
        try {
            User savedUser = userServiceImp.createUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            String token = JwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse(token, "Registration successful.");
            return ResponseEntity.ok(authResponse);
        } catch (UserException e) {
            // Handle user creation failure (e.g., user already exists)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(null, "User already exists: " + e.getMessage()));
        } catch (Exception e) {
            // General error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> userSignIn(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            String token = JwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse(token, "Login successful.");
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid email or password."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, "Login failed: " + e.getMessage()));
        }
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetail = userDetailService.loadUserByUsername(email);
        if (userDetail == null) {
            throw new BadCredentialsException("Invalid user");
        }
        if (!passwordEncoder.matches(password, userDetail.getPassword())) {
            throw new BadCredentialsException("Incorrect password.");
        }
        return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
    }
}
