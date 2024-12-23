package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.Post;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.response.ApiResponse;
import com.ankit.socialmedia.service.implementation.PostServiceImp;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostServiceImp postServiceImp;
    @Resource
    private UserServiceImp userServiceImp;
    @PostMapping("/api/create-post")
    public ResponseEntity<Post> createPost(@RequestBody Post post,@RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userServiceImp.findUserByToken(jwt);
        Post newPost = postServiceImp.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userServiceImp.findUserByToken(jwt);
        String message = postServiceImp.deletePost(postId, reqUser.getId());

        ApiResponse response = new ApiResponse(message,true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Long postId) throws Exception {
        Post post = postServiceImp.findPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @GetMapping("/post/user/{userId}")
    public ResponseEntity<List<Post>> findUserPost(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().body(null); // Handle invalid userId
        }
        List<Post> posts = postServiceImp.findPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPosts(){
        List<Post> posts = postServiceImp.findAllPost();
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    @PostMapping("/post/{postId}")
    public ResponseEntity<Post> savedPostHandler(@RequestHeader("Authorization") String jwt , @PathVariable Long postId) throws Exception {
        User reqUser = userServiceImp.findUserByToken(jwt);
        Post post = postServiceImp.postSaved(reqUser.getId(), postId);
        return new ResponseEntity<Post>(post,HttpStatus.OK);
    }
    @PostMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likedPostHandler(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userServiceImp.findUserByToken(jwt);
        Post post = postServiceImp.likedPost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}
