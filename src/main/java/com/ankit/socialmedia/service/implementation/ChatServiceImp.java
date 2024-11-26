package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.repository.ChatRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImp implements ChatService{
    @Resource
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExist = chatRepository.findChatByUserId(user2,reqUser);
        if(isExist != null) return isExist;
        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimeStamp(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Long chatId) throws Exception {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isEmpty()) throw new Exception("Chat Not found with User "+chatRepository.findById(chatId));
        return chat.get();
    }

    @Override
    public List<Chat> userChat(Long userId) {
        return chatRepository.findChatsByUserId(userId);
    }
}
