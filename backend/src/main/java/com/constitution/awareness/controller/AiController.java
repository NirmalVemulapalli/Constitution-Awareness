package com.constitution.awareness.controller;


import com.constitution.awareness.dto.AiChatRequest;
import com.constitution.awareness.dto.AiChatResponse;
import com.constitution.awareness.service.AiService;


import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ai")
@CrossOrigin(
        origins = "${app.cors.allowed-origins:http://localhost:5173}"
)
public class AiController {


    private final AiService aiService;


    public AiController(
            AiService aiService
    ) {

        this.aiService =
                aiService;
    }


    /*
     * =====================================
     * AI CHAT
     *
     * POST
     * /api/ai/chat
     *
     * sessionId = null
     * → Creates new conversation
     *
     * sessionId = existing ID
     * → Continues existing conversation
     * =====================================
     */

    @PostMapping("/chat")
    public ResponseEntity<AiChatResponse> chat(

            @Valid
            @RequestBody
            AiChatRequest request

    ) {

        AiChatResponse response =

                aiService.processQuestion(
                        request
                );


        return ResponseEntity.ok(
                response
        );
    }
}