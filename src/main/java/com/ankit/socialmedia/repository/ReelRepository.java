package com.ankit.socialmedia.repository;

import com.ankit.socialmedia.Model.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reels, Long> {
    public List<Reels> findReelsByUserId(Long userId);
}
