package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.UserException;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    public User createUser(User user) throws UserException;
    public User findUserById(Long userID) throws UserException;
    public User findUserByEmail(String email);
    public User followUser(Long userId, Long userID ) throws UserException;
    public User updateUserById(User user, Long Id) throws UserException;
    public List<User> searchUser(String query);
    public User findUserByToken(String jwt) throws UserException;

}
