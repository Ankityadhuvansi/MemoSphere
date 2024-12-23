package com.ankit.socialmedia.repository;

import com.ankit.socialmedia.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.id = :userId")
    public List<Post> findPostByUserId(@Param("userId") Long userId);
}
