package com.ankit.socialmedia.repository;

import com.ankit.socialmedia.Model.Chat;
import com.ankit.socialmedia.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    // Retrieve all chats where a specific user is a participant
    @Query("select c from Chat c join c.users u where u.id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") Long userId);

    // Retrieve a chat between two specific users
    @Query("select c from Chat c where :user member of c.users and :reqUser member of c.users")
    Chat findChatByUserId(@Param("user") User user, @Param("reqUser") User reqUser);
}