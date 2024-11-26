package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.configuration.JwtProvider;
import com.ankit.socialmedia.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User newUser) throws Exception {
        User isExist = userRepository.findByEmail(newUser.getEmail());
        if(isExist != null){
            throw new Exception("User Already Exist" +isExist);
        }
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setGender(newUser.getGender());

        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long userID) {
        Optional<User> userFound = userRepository.findById(userID);
        if (userFound.isPresent()) {
            return userFound.get();
        }
        throw new RuntimeException("User does not exist with ID: " + userID);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User followUser(Long reqUserId, Long followUserId) {
        User reqUser = findUserById(reqUserId);
        User followedUser = findUserById(followUserId);
        // Ensure the user is not already following the followedUser
        if (!followedUser.getFollowers().contains(reqUser)) {
            followedUser.getFollowers().add(reqUser); // Add follower to followedUser's followers
            reqUser.getFollowings().add(followedUser); // Add followedUser to follower's followings
        }
        userRepository.save(reqUser);
        userRepository.save(followedUser);

        return reqUser;
    }

    @Override
    public User updateUserById(User user, Long id) {
        Optional<User> userPresent = userRepository.findById(id);
        if (userPresent.isPresent()) {
            User userExist = userPresent.get();
            userExist.setEmail(user.getEmail());
            userExist.setFirstName(user.getFirstName());
            userExist.setLastName(user.getLastName());
            userExist.setPassword(user.getPassword());
            userExist.setGender(user.getGender());
            return userRepository.save(userExist);
        }
        throw new RuntimeException("User not found with ID: " + id);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByToken(String jwt) {
        String email  = JwtProvider.extractEmailByToken(jwt);

        User user = userRepository.findByEmail(email);
        return user;
    }
}