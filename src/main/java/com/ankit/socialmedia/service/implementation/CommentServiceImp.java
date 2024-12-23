package com.ankit.socialmedia.service.implementation;

import com.ankit.socialmedia.Model.Comment;
import com.ankit.socialmedia.Model.Post;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.ChatException;
import com.ankit.socialmedia.exception.CommentException;
import com.ankit.socialmedia.exception.PostException;
import com.ankit.socialmedia.exception.UserException;
import com.ankit.socialmedia.repository.CommentRepository;
import com.ankit.socialmedia.repository.PostRepository;
import com.ankit.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private PostServiceImp postServiceImp;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public Comment createComments(Comment comment, Long postId, Long userId) throws CommentException, UserException, PostException {
        User user = userServiceImp.findUserById(userId);
        Post post = postServiceImp.findPostById(postId);
        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        postRepository.save(post);
        return savedComment;
    }

    @Override
    public Comment likeComment(Long commentId, Long userId) throws CommentException, UserException {
        Comment comment = findCommentById(commentId);
        User user = userServiceImp.findUserById(userId);
        if(!comment.getLikes().contains(user)){
            comment.getLikes().add(user);
        }else comment.getLikes().remove(user);
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Long commentId) throws CommentException {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) throw new CommentException("Comment Doesn't exist");

        return optionalComment.get();
    }
}
