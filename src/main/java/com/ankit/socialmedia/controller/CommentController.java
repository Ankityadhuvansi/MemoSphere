package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.Comment;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.service.implementation.CommentServiceImp;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Resource
    private CommentServiceImp commentServiceImp;
    @Resource
    private UserServiceImp userServiceImp;
    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt, @PathVariable Long postId) throws Exception {
        User user = userServiceImp.findUserByToken(jwt);
        Comment createdcomment = commentServiceImp.createComments(comment,postId, user.getId());
        return createdcomment;
    }

    @PostMapping("/api/like-comment/{commentId}")
    public Comment LikeComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userServiceImp.findUserByToken(jwt);
        Comment likeComment = commentServiceImp.likeComment(commentId, user.getId());
        return likeComment;
    }
}
