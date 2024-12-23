package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Post;
import com.ankit.socialmedia.exception.PostException;
import com.ankit.socialmedia.exception.UserException;

import java.util.List;

public interface PostService {
    public Post createNewPost(Post post, Long userId) throws PostException, UserException;

    public String deletePost(Long postId, Long userId) throws PostException, UserException;

    public List<Post> findPostByUserId(Long userId);

    public Post findPostById(Long postId) throws PostException;

    public List<Post> findAllPost();

    public Post postSaved(Long postId, Long userId) throws PostException, UserException;
    public Post likedPost(Long postId, Long userId) throws PostException, UserException;
}
