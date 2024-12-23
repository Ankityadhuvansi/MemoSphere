package com.ankit.socialmedia.controller;

import com.ankit.socialmedia.Model.Message;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.service.implementation.ChatServiceImp;
import com.ankit.socialmedia.service.implementation.MessageServiceImp;
import com.ankit.socialmedia.service.implementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    public MessageServiceImp messageServiceImp;
    @Autowired
    private ChatServiceImp chatServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;
    @PostMapping("/create-message/chat/{chatId}")
    public Message createMessage(@RequestBody Message reqMessage,
                                 @PathVariable Long chatId,
                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userServiceImp.findUserByToken(jwt);
        Message message = messageServiceImp.createMessage(user,chatId,reqMessage);
        return message;
    }

    @GetMapping("/message/chat/chatId")
    public List<Message> findChatMessages(@RequestHeader("Authorization") String jwt, @PathVariable Long chatId) throws Exception {
        User user = userServiceImp.findUserByToken(jwt);
        return messageServiceImp.findChatMessages(chatId);
    }
}
