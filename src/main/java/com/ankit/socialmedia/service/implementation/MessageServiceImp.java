package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService{
    @Override
    public Message createMessage(Long userId, Long chatId, Chat chat) {
        return null;
    }

    @Override
    public List<Message> findChatMessages(Long chatId) {
        return List.of();
    }
}
