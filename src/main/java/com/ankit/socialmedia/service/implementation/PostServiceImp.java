package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Post;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.repository.PostRepository;
import com.ankit.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserServiceImp userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Post createNewPost(Post post, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);
        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Long postId, Long userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getUser().getId() != user.getId()){
            throw new Exception("You can't delete other User's post");
        }
        postRepository.deleteById(postId);
        return "Post deleted Successfully";
    }

    @Override
    public List<Post> findPostByUserId(Long userId) {

        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Long postId) throws Exception {
        Optional<Post> opt = postRepository.findById(postId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new Exception("Post Not found with id"+ postId);
    }

    @Override
    public List<Post> findAllPost() {

        return postRepository.findAll();
    }

    @Override
    public Post postSaved(Long postId, Long userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
        }else user.getSavedPosts().add(post);

        userRepository.save(user);
        return post;
    }
    @Override
    @Transactional
    public Post likedPost(Long postId, Long userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (user == null) {
            throw new Exception("User not found with id " + userId);
        }

        if (post.getLikes() == null) {
            post.setLikes(new HashSet<>());
        }

        if (post.getLikes().contains(user)) {
            post.getLikes().remove(user);
        } else {
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }
}
