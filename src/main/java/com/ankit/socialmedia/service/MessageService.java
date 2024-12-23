package com.ankit.socialmedia.service;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.Message;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.ChatException;
import com.ankit.socialmedia.exception.MessageException;

import java.util.List;

public interface MessageService {
    public Message createMessage(User user, Long chatId, Message reqMessage) throws MessageException, ChatException;
    public List<Message> findChatMessages(Long chatId) throws MessageException;
}
