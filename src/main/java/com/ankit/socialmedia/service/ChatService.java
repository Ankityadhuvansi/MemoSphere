package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.ChatException;

import java.util.List;

public interface ChatService {
    public Chat createChat(User reqUser, User user2);
    public Chat findChatById(Long chatId) throws ChatException;
    public List<Chat> userChat(Long userId);
}
