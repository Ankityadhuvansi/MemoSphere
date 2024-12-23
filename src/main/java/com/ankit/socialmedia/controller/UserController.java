package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.UserException;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserServiceImp userServiceImp;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServiceImp.getAllUser();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) throws UserException {
        return userServiceImp.findUserById(id);
    }

    @PutMapping("/user-update")
    public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userServiceImp.findUserByToken(jwt);
        user.setPassword(reqUser.getPassword());
        return userServiceImp.updateUserById(user, reqUser.getId());
    }

    @PutMapping("/user/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long userId2) throws UserException {
        User reqUser = userServiceImp.findUserByToken(jwt);
        return userServiceImp.followUser(reqUser.getId(), userId2);
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userServiceImp.searchUser(query);
    }
    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserFromToken(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userServiceImp.findUserByToken(jwt);
        return ResponseEntity.ok(user);
    }
}