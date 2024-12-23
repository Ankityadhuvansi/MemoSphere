package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Comment;
import com.ankit.socialmedia.exception.CommentException;
import com.ankit.socialmedia.exception.PostException;
import com.ankit.socialmedia.exception.UserException;

public interface CommentService {

    public Comment createComments(Comment comment, Long PostId, Long userId) throws CommentException, UserException, PostException;
    public Comment likeComment(Long commentId, Long postId) throws CommentException, UserException;
    public Comment findCommentById(Long commentId) throws CommentException;
}
