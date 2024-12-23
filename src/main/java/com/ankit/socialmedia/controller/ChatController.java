package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.UserException;
import com.ankit.socialmedia.request.ChatRequest;
import com.ankit.socialmedia.service.implementation.ChatServiceImp;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {
    @Resource
    private ChatServiceImp chatServiceImp;
    @Resource
    private UserServiceImp userServiceImp;
    @PostMapping("/create-chats")
    private Chat createChat(@RequestHeader("Authorization") String jwt,@RequestBody ChatRequest request) throws UserException {

        User reqUser = userServiceImp.findUserByToken(jwt);
        System.out.println("Requested User :" +reqUser);
        User otherUser = userServiceImp.findUserById(request.getUserId());
        Chat chat = chatServiceImp.createChat(reqUser, otherUser);
        return chat;
    }
    @GetMapping("chats")
    private List<Chat> findUserChat(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userServiceImp.findUserByToken(jwt);
        List<Chat> chats =  chatServiceImp.userChat(user.getId());
        return chats;
    }

}
