package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.Reels;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.UserException;
import com.ankit.socialmedia.service.implementation.ReelServiceImp;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReelsController {
    @Resource
    private ReelServiceImp reelServiceImp;
    @Resource
    private UserServiceImp userServiceImp;
    @PostMapping("/create-reels")
    public Reels createReels(@RequestBody Reels reels, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userServiceImp.findUserByToken(jwt);
        return reelServiceImp.createReels(reels,user);
    }
    @GetMapping("/reels")
    public List<Reels> getAllReels(){
        return reelServiceImp.findAllReels();
    }
    @GetMapping("/user-reels")
    public List<Reels> UserReels(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userServiceImp.findUserByToken(jwt);
        return reelServiceImp.findUserReels(user.getId());
    }

}
