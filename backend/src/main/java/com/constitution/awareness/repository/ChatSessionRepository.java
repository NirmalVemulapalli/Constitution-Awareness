package com.constitution.awareness.repository;

import com.constitution.awareness.entity.ChatSession;
import com.constitution.awareness.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChatSessionRepository
        extends JpaRepository<ChatSession, Long> {


    /*
     * Find a session belonging to a specific user.
     */

    Optional<ChatSession> findByIdAndUser(
            Long sessionId,
            User user
    );


    /*
     * Get user's conversations.
     *
     * Latest conversation first.
     */

    List<ChatSession> findByUserOrderByUpdatedAtDesc(
            User user
    );
}