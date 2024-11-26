package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.service.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Resource
    private UserServiceImp userServiceImp;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServiceImp.getAllUser();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userServiceImp.findUserById(id);
    }

    @PutMapping("/user-update/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        return userServiceImp.updateUser(user, id);
    }

    @PutMapping("/users/follow/{userId1}/{userId2}")
    public User followUserHandler(@PathVariable Long userId1, @PathVariable Long userId2) {
        return userServiceImp.followUser(userId1, userId2);
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userServiceImp.searchUser(query);
    }

    // Uncomment these methods if needed
    // @PutMapping("/user-update")
    // public void updateUser(@RequestBody User user) {
    //     userServiceImp.save(user);
    // }

    // @DeleteMapping("/delete-user/{id}")
    // public void deleteUser(@PathVariable Long id) {
    //     userServiceImp.deleteUser(id);
    // }
}