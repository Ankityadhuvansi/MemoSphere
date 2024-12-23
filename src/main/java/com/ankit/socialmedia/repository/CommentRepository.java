package com.ankit.socialmedia.repository;

import com.ankit.socialmedia.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
