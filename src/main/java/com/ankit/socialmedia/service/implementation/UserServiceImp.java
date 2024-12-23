package com.ankit.socialmedia.service.implementation;

import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.configuration.JwtProvider;
import com.ankit.socialmedia.exception.UserException;
import com.ankit.socialmedia.repository.UserRepository;
import com.ankit.socialmedia.service.UserService;
import jakarta.annotation.Resource;
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

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User newUser) throws UserException {
        User isExist = userRepository.findByEmail(newUser.getEmail());
        if (isExist != null) {
            throw new UserException("User Already Exists: " + isExist);
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
    public User findUserById(Long userID) throws UserException {
        Optional<User> userFound = userRepository.findById(userID);
        if (userFound.isPresent()) {
            return userFound.get();
        }
        throw new UserException("User not found with ID: " + userID);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User followUser(Long reqUserId, Long followUserId) throws UserException {
        if (reqUserId.equals(followUserId)) {
            throw new UserException("User cannot follow themselves");
        }

        User reqUser = findUserById(reqUserId);
        User followedUser = findUserById(followUserId);

        if (!followedUser.getFollowers().contains(reqUser)) {
            followedUser.getFollowers().add(reqUser);
            reqUser.getFollowings().add(followedUser);
        }
        userRepository.save(reqUser);
        userRepository.save(followedUser);

        return reqUser;
    }

    @Override
    public User updateUserById(User user, Long id) throws UserException {
        Optional<User> userPresent = userRepository.findById(id);
        if (userPresent.isPresent()) {
            User userExist = userPresent.get();
            userExist.setEmail(user.getEmail());
            userExist.setFirstName(user.getFirstName());
            userExist.setLastName(user.getLastName());
            userExist.setPassword(passwordEncoder.encode(user.getPassword())); // Ensure password is encoded
            userExist.setGender(user.getGender());
            return userRepository.save(userExist);
        }
        throw new UserException("User not found with ID: " + id);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query); // Define search logic in the repository
    }

    @Override
    public User findUserByToken(String jwt) throws UserException {
        try {
            String email = JwtProvider.extractEmailByToken(jwt);
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UserException("User not found");
            }
            return user;
        } catch (Exception e) {
            throw new UserException("Invalid token or user not found");
        }
    }
}