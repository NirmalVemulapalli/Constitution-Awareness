package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "chat_sessions")
public class ChatSession {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    /*
     * Each chat session belongs to one user.
     */

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    /*
     * Messages belonging to this session.
     */

    @OneToMany(
            mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChatMessage> messages =
            new ArrayList<>();


    /*
     * When the conversation was created.
     */

    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;


    /*
     * When the conversation was last active.
     */

    @Column(
            nullable = false
    )
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {

        createdAt =
                LocalDateTime.now();

        updatedAt =
                LocalDateTime.now();
    }


    @PreUpdate
    protected void onUpdate() {

        updatedAt =
                LocalDateTime.now();
    }


    /*
     * Getters and Setters
     */

    public Long getId() {

        return id;
    }


    public void setId(
            Long id
    ) {

        this.id = id;
    }


    public User getUser() {

        return user;
    }


    public void setUser(
            User user
    ) {

        this.user = user;
    }


    public List<ChatMessage> getMessages() {

        return messages;
    }


    public void setMessages(
            List<ChatMessage> messages
    ) {

        this.messages = messages;
    }


    public LocalDateTime getCreatedAt() {

        return createdAt;
    }


    public void setCreatedAt(
            LocalDateTime createdAt
    ) {

        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {

        return updatedAt;
    }


    public void setUpdatedAt(
            LocalDateTime updatedAt
    ) {

        this.updatedAt = updatedAt;
    }
}