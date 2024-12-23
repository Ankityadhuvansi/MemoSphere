package com.ankit.socialmedia.service.implementation;

import com.ankit.socialmedia.Model.Reels;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.repository.ReelRepository;
import com.ankit.socialmedia.service.ReelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReelServiceImp implements ReelService {
    @Resource
    private ReelRepository reelRepository;
    @Resource
    private UserServiceImp userServiceImp;
    @Override
    public Reels createReels(Reels reels, User user) {
        Reels newReels = new Reels();
        newReels.setVideo(reels.getVideo());
        newReels.setCaption(reels.getCaption());
        newReels.setCreatedAt(LocalDateTime.now());
        newReels.setUser(user);
        return reelRepository.save(newReels);
    }

    @Override
    public List<Reels> findAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public List<Reels> findUserReels(Long userId) {
        return reelRepository.findReelsByUserId(userId);
    }

//    @Override
//    public Reels likeReels(Long reelsId, Long userId) {
//        return null;
//    }
//
//    @Override
//    public Reels commentReels(Long reelsId, Comment comment) {
//        return null;
//    }
}
