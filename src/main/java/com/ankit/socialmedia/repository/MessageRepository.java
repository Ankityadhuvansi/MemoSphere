package com.ankit.socialmedia.repository;

import com.ankit.socialmedia.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findByChatId(Long chatId);
}
