package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Reels;
import com.ankit.socialmedia.Model.User;

import java.util.List;

public interface ReelService {
    public Reels createReels(Reels reels, User user);
    public List<Reels> findAllReels();
    public List<Reels> findUserReels(Long userId);
//    public Reels likeReels(Long reelsId, Long userId);
//    public Reels commentReels(Long reelsId, Comment comment);
}
