package com.constitution.awareness.repository;

import com.constitution.awareness.entity.ChatMessage;
import com.constitution.awareness.entity.ChatSession;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, Long> {


    /*
     * Get all messages from a session.
     *
     * Oldest message first so the AI receives
     * the conversation in correct order.
     */

    List<ChatMessage> findBySessionOrderByCreatedAtAsc(
            ChatSession session
    );
}