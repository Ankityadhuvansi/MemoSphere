package com.ankit.socialmedia.service.implementation;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.Message;
import com.ankit.socialmedia.Model.User;
import com.ankit.socialmedia.exception.ChatException;
import com.ankit.socialmedia.exception.MessageException;
import com.ankit.socialmedia.repository.ChatRepository;
import com.ankit.socialmedia.repository.MessageRepository;
import com.ankit.socialmedia.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    private ChatServiceImp chatService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message createMessage(User user, Long chatId, Message req) throws MessageException, ChatException {
        Message message = new Message();
        Chat chat = chatService.findChatById(chatId);
        message.setChat(chat);
        message.setContent(req.getContent());
        message.setImage(req.getImage());
        message.setUser(user);
        message.setTimeStamp(LocalDateTime.now());
        Message savedMessage =  messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return savedMessage;
    }

    @Override
    public List<Message> findChatMessages(Long chatId) throws MessageException {
        return messageRepository.findByChatId(chatId);

    }
}
