package com.constitution.awareness.service;

import com.constitution.awareness.dto.AiChatRequest;
import com.constitution.awareness.dto.AiChatResponse;
import com.constitution.awareness.entity.Article;
import com.constitution.awareness.entity.ChatMessage;
import com.constitution.awareness.entity.ChatSession;
import com.constitution.awareness.entity.MessageIntent;
import com.constitution.awareness.entity.MessageRole;
import com.constitution.awareness.entity.User;
import com.constitution.awareness.repository.ArticleRepository;
import com.constitution.awareness.repository.ChatMessageRepository;
import com.constitution.awareness.repository.ChatSessionRepository;
import com.constitution.awareness.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class AiService {


    private final RestTemplate restTemplate;

    private final ArticleRepository articleRepository;

    private final ChatSessionRepository chatSessionRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final UserRepository userRepository;


    /*
     * =====================================
     * GROQ CONFIGURATION
     * =====================================
     */

    @Value("${groq.api.key}")
    private String groqApiKey;


    @Value("${groq.api.url}")
    private String groqApiUrl;


    @Value("${groq.model}")
    private String groqModel;


    /*
     * =====================================
     * CONSTRUCTOR
     * =====================================
     */

    public AiService(

            RestTemplate restTemplate,

            ArticleRepository articleRepository,

            ChatSessionRepository chatSessionRepository,

            ChatMessageRepository chatMessageRepository,

            UserRepository userRepository

    ) {

        this.restTemplate = restTemplate;

        this.articleRepository = articleRepository;

        this.chatSessionRepository = chatSessionRepository;

        this.chatMessageRepository = chatMessageRepository;

        this.userRepository = userRepository;
    }


    /*
     * =====================================
     * PROCESS USER QUESTION
     * =====================================
     */

    public AiChatResponse processQuestion(
            AiChatRequest request
    ) {


        /*
         * GET QUESTION
         */

        String question = request.getQuestion();


        /*
         * VALIDATE QUESTION
         */

        if (

                question == null

                        ||

                question.trim().isEmpty()

        ) {

            return new AiChatResponse(

                    "Please ask a question.",

                    null,

                    request.getSessionId()

            );
        }


        question = question.trim();


        /*
         * GET LOGGED-IN USER
         */

        Authentication authentication =

                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (

                authentication == null

                        ||

                !authentication.isAuthenticated()

                        ||

                "anonymousUser".equals(
                        authentication.getPrincipal()
                )

        ) {

            return new AiChatResponse(

                    "Please log in to use the AI assistant.",

                    null,

                    null

            );
        }


        String userEmail = authentication.getName();


        User user =

                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(

                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )

                        );


        /*
         * =====================================
         * FIND OR CREATE CHAT SESSION
         * =====================================
         */

        ChatSession chatSession;


        if (
                request.getSessionId() != null
        ) {

            chatSession =

                    chatSessionRepository
                            .findByIdAndUser(

                                    request.getSessionId(),

                                    user

                            )
                            .orElseThrow(

                                    () ->
                                            new RuntimeException(
                                                    "Chat session not found"
                                            )

                            );

        }

        else {

            chatSession = new ChatSession();


            chatSession.setUser(user);


            chatSession =

                    chatSessionRepository.save(
                            chatSession
                    );
        }


        /*
         * =====================================
         * LOAD PREVIOUS CONVERSATION
         *
         * IMPORTANT:
         * Load before saving current message.
         * =====================================
         */

        List<ChatMessage> previousMessages =

                chatMessageRepository
                        .findBySessionOrderByCreatedAtAsc(
                                chatSession
                        );


        /*
         * =====================================
         * SAVE USER MESSAGE
         * =====================================
         */

        ChatMessage userMessage =
                new ChatMessage();


        userMessage.setSession(chatSession);

        userMessage.setRole(MessageRole.USER);

        userMessage.setContent(question);


        chatMessageRepository.save(
                userMessage
        );


        /*
         * =====================================
         * DETECT MESSAGE INTENT
         * =====================================
         */

        MessageIntent intent =

                detectIntent(

                        question,

                        previousMessages

                );


        /*
         * =====================================
         * HANDLE SIMPLE CASUAL MESSAGES
         *
         * No Groq call.
         * Instant response.
         * =====================================
         */

        if (
                intent == MessageIntent.CASUAL
        ) {

            String answer =

                    generateCasualResponse(

                            question,

                            previousMessages

                    );


            saveAssistantMessage(

                    chatSession,

                    answer

            );


            updateSessionTimestamp(
                    chatSession
            );


            return new AiChatResponse(

                    answer,

                    null,

                    chatSession.getId()

            );
        }


        /*
         * =====================================
         * FIND RELEVANT ARTICLES
         *
         * Database is used as additional
         * context, not as a restriction.
         * =====================================
         */

        List<Article> relevantArticles =
                new ArrayList<>();


        /*
         * =====================================
         * CONSTITUTION QUESTION
         * =====================================
         */

        if (
                intent == MessageIntent.CONSTITUTION
        ) {

            relevantArticles =

                    findRelevantArticles(
                            question
                    );
        }


        /*
         * =====================================
         * FOLLOW-UP QUESTION
         *
         * Try to find the previously
         * discussed article.
         * =====================================
         */

        if (

                relevantArticles.isEmpty()

                        &&

                (

                        intent == MessageIntent.FOLLOW_UP

                                ||

                        intent == MessageIntent.RESPONSE_MODIFICATION

                )

        ) {

            relevantArticles =

                    findArticlesFromConversation(
                            previousMessages
                    );
        }


        /*
         * =====================================
         * GENERAL QUESTION
         *
         * Try article matching only as
         * a safety net.
         * =====================================
         */

        if (

                relevantArticles.isEmpty()

                        &&

                intent == MessageIntent.GENERAL

        ) {

            relevantArticles =

                    findRelevantArticles(
                            question
                    );
        }


        /*
         * =====================================
         * BUILD CONSTITUTION CONTEXT
         * =====================================
         */

        String constitutionContext =

                buildConstitutionContext(
                        relevantArticles
                );


        /*
         * =====================================
         * BUILD SHORT CONVERSATION HISTORY
         *
         * Only last 4 messages for speed.
         * =====================================
         */

        String conversationHistory =

                buildConversationHistory(
                        previousMessages
                );


        /*
         * =====================================
         * BUILD AI PROMPT
         * =====================================
         */

        String prompt =

                buildPrompt(

                        question,

                        constitutionContext,

                        conversationHistory,

                        intent

                );


        /*
         * =====================================
         * ASK GROQ
         * =====================================
         */

        String answer =

                askGroq(

                        prompt,

                        intent

                );


        /*
         * =====================================
         * RELATED ARTICLE
         * =====================================
         */

        String relatedArticle =
                null;


        if (

                !relevantArticles.isEmpty()

                        &&

                !isConversationEnding(
                        question
                )

        ) {

            Article article =
                    relevantArticles.get(0);


            relatedArticle =

                    article.getArticleNumber()

                            + " — "

                            + article.getTitle();
        }


        /*
         * =====================================
         * SAVE AI RESPONSE
         * =====================================
         */

        saveAssistantMessage(

                chatSession,

                answer

        );


        /*
         * =====================================
         * UPDATE SESSION TIME
         * =====================================
         */

        updateSessionTimestamp(
                chatSession
        );


        /*
         * =====================================
         * RETURN RESPONSE
         * =====================================
         */

        return new AiChatResponse(

                answer,

                relatedArticle,

                chatSession.getId()

        );
    }


    /*
     * =====================================
     * DETECT MESSAGE INTENT
     * =====================================
     */

    private MessageIntent detectIntent(

            String question,

            List<ChatMessage> previousMessages

    ) {


        String normalized =

                question
                        .toLowerCase()
                        .trim()
                        .replaceAll(
                                "\\s+",
                                " "
                        );


        /*
         * PRIORITY 1:
         * CASUAL CONVERSATION
         */

        if (
                isSimpleConversation(question)
        ) {

            return MessageIntent.CASUAL;
        }


        boolean hasPreviousConversation =

                previousMessages != null

                        &&

                !previousMessages.isEmpty();


        /*
         * PRIORITY 2:
         * RESPONSE MODIFICATION
         */

        List<String> modificationIndicators =

                Arrays.asList(

                        "short",

                        "shorter",

                        "make it short",

                        "make it shorter",

                        "brief",

                        "briefly",

                        "simple",

                        "simpler",

                        "make it simple",

                        "make it simpler",

                        "explain more",

                        "tell me more",

                        "more detail",

                        "detailed",

                        "summarize",

                        "summary",

                        "not this much short"

                );


        if (

                hasPreviousConversation

                        &&

                modificationIndicators.stream()
                        .anyMatch(

                                indicator ->

                                        normalized.equals(indicator)

                                                ||

                                        (

                                                normalized.length() < 40

                                                        &&

                                                normalized.contains(
                                                        indicator
                                                )

                                        )

                        )

        ) {

            return MessageIntent.RESPONSE_MODIFICATION;
        }


        /*
         * PRIORITY 3:
         * CONSTITUTION QUESTION
         */

        if (

                isConstitutionQuestion(question)

                        ||

                normalized.matches(
                        ".*\\barticle\\s+\\d+[a-zA-Z]?.*"
                )

        ) {

            return MessageIntent.CONSTITUTION;
        }


        /*
         * PRIORITY 4:
         * FOLLOW-UP
         */

        if (

                hasPreviousConversation

                        &&

                isFollowUpQuestion(question)

        ) {

            return MessageIntent.FOLLOW_UP;
        }


        /*
         * DEFAULT
         */

        return MessageIntent.GENERAL;
    }


    /*
     * =====================================
     * SIMPLE CONVERSATION DETECTION
     * =====================================
     */

    private boolean isSimpleConversation(
            String question
    ) {


        String normalized =

                question
                        .toLowerCase()
                        .trim()
                        .replaceAll(
                                "[^a-z0-9 ]",
                                ""
                        );


        List<String> casualMessages =

                Arrays.asList(

                        "hello",

                        "hi",

                        "hey",

                        "hii",

                        "namaste",

                        "good morning",

                        "good afternoon",

                        "good evening",

                        "how are you",

                        "whats up",

                        "thanks",

                        "thank you",

                        "thankyou",

                        "thanks a lot",

                        "okay",

                        "ok",

                        "cool",

                        "great",

                        "good",

                        "nice",

                        "bye",

                        "goodbye",

                        "see you",

                        "nothing",

                        "never mind",

                        "thats it",

                        "no need"

                );


        return casualMessages.contains(
                normalized
        );
    }


    /*
     * =====================================
     * CONVERSATION END DETECTION
     * =====================================
     */

    private boolean isConversationEnding(
            String question
    ) {


        String normalized =

                question
                        .toLowerCase()
                        .trim();


        List<String> endings =

                Arrays.asList(

                        "that's it",

                        "thats it",

                        "no need",

                        "nothing else",

                        "bye",

                        "goodbye",

                        "never mind"

                );


        return endings.stream()
                .anyMatch(
                        normalized::contains
                );
    }


    /*
     * =====================================
     * GENERATE CASUAL RESPONSE
     * =====================================
     */

    private String generateCasualResponse(

            String question,

            List<ChatMessage> previousMessages

    ) {


        String normalized =

                question
                        .toLowerCase()
                        .trim()
                        .replaceAll(
                                "[^a-z0-9 ]",
                                ""
                        );


        boolean isFirstMessage =

                previousMessages == null

                        ||

                previousMessages.isEmpty();


        /*
         * GREETINGS
         */

        if (

                normalized.equals("hello")

                        ||

                normalized.equals("hi")

                        ||

                normalized.equals("hey")

                        ||

                normalized.equals("hii")

                        ||

                normalized.equals("namaste")

        ) {

            if (isFirstMessage) {

                return """
                        Hello! 👋 I'm your AI assistant. I can help with
                        Indian Constitution topics and general questions.
                        What would you like to know?
                        """;
            }


            return "Hello again! How can I help you?";
        }


        /*
         * TIME GREETINGS
         */

        if (

                normalized.equals("good morning")

                        ||

                normalized.equals("good afternoon")

                        ||

                normalized.equals("good evening")

        ) {

            return "Good to see you! How can I help you today?";
        }


        /*
         * HOW ARE YOU
         */

        if (

                normalized.equals("how are you")

                        ||

                normalized.equals("whats up")

        ) {

            return "I'm doing well, thank you! Ready to help with your questions.";
        }


        /*
         * THANK YOU
         */

        if (

                normalized.equals("thanks")

                        ||

                normalized.equals("thank you")

                        ||

                normalized.equals("thankyou")

                        ||

                normalized.equals("thanks a lot")

        ) {

            return "You're welcome! 😊";
        }


        /*
         * OKAY
         */

        if (

                normalized.equals("okay")

                        ||

                normalized.equals("ok")

        ) {

            return "Sure!";
        }


        /*
         * POSITIVE RESPONSE
         */

        if (

                normalized.equals("cool")

                        ||

                normalized.equals("nice")

                        ||

                normalized.equals("good")

                        ||

                normalized.equals("great")

        ) {

            return "Glad to hear that!";
        }


        /*
         * GOODBYE
         */

        if (

                normalized.equals("bye")

                        ||

                normalized.equals("goodbye")

                        ||

                normalized.equals("see you")

        ) {

            return "Bye! Take care. 👋";
        }


        return "Sure! How can I help you?";
    }


    /*
     * =====================================
     * SAVE ASSISTANT MESSAGE
     * =====================================
     */

    private void saveAssistantMessage(

            ChatSession chatSession,

            String answer

    ) {


        ChatMessage assistantMessage =
                new ChatMessage();


        assistantMessage.setSession(
                chatSession
        );


        assistantMessage.setRole(
                MessageRole.ASSISTANT
        );


        assistantMessage.setContent(
                answer
        );


        chatMessageRepository.save(
                assistantMessage
        );
    }


    /*
     * =====================================
     * UPDATE SESSION TIMESTAMP
     * =====================================
     */

    private void updateSessionTimestamp(
            ChatSession chatSession
    ) {


        chatSession.setUpdatedAt(
                LocalDateTime.now()
        );


        chatSessionRepository.save(
                chatSession
        );
    }


    /*
     * =====================================
     * BUILD CONVERSATION HISTORY
     * =====================================
     */

    private String buildConversationHistory(
            List<ChatMessage> messages
    ) {


        if (

                messages == null

                        ||

                messages.isEmpty()

        ) {

            return "None.";
        }


        StringBuilder history =
                new StringBuilder();


        /*
         * Only last 4 messages.
         */

        int startIndex =

                Math.max(

                        0,

                        messages.size() - 4

                );


        for (

                int i = startIndex;

                i < messages.size();

                i++

        ) {


            ChatMessage message =
                    messages.get(i);


            String role =

                    message.getRole()
                            == MessageRole.USER

                            ?

                            "User"

                            :

                            "Assistant";


            history
                    .append(role)
                    .append(": ")
                    .append(
                            message.getContent()
                    )
                    .append("\n");
        }


        return history.toString();
    }


    /*
     * =====================================
     * FIND ARTICLE FROM CONVERSATION
     * =====================================
     */

    private List<Article> findArticlesFromConversation(
            List<ChatMessage> messages
    ) {


        if (

                messages == null

                        ||

                messages.isEmpty()

        ) {

            return new ArrayList<>();
        }


        Pattern pattern =

                Pattern.compile(

                        "\\bArticle\\s+(\\d+[a-zA-Z]?)\\b",

                        Pattern.CASE_INSENSITIVE

                );


        for (

                int i = messages.size() - 1;

                i >= 0;

                i--

        ) {


            ChatMessage message =
                    messages.get(i);


            Matcher matcher =

                    pattern.matcher(
                            message.getContent()
                    );


            if (
                    matcher.find()
            ) {


                String articleNumber =

                        "Article "

                                +

                        matcher.group(1);


                Optional<Article> article =

                        articleRepository
                                .findByArticleNumber(
                                        articleNumber
                                );


                if (

                        article.isPresent()

                                &&

                        article.get()
                                .isPublished()

                ) {

                    return List.of(
                            article.get()
                    );
                }
            }
        }


        return new ArrayList<>();
    }


    /*
     * =====================================
     * FOLLOW-UP DETECTION
     * =====================================
     */

    private boolean isFollowUpQuestion(
            String question
    ) {


        String normalized =

                question
                        .toLowerCase()
                        .trim();


        List<String> indicators =

                Arrays.asList(

                        "it",

                        "this",

                        "that",

                        "example",

                        "give example",

                        "give an example",

                        "what does it mean",

                        "why",

                        "how",

                        "can you explain",

                        "explain this",

                        "what about that",

                        "tell me about it",

                        "what next"

                );


        return indicators.stream()
                .anyMatch(

                        indicator ->

                                normalized.equals(
                                        indicator
                                )

                                        ||

                                (

                                        normalized.length() < 45

                                                &&

                                        normalized.contains(
                                                indicator
                                        )

                                )

                );
    }


    /*
     * =====================================
     * FIND RELEVANT ARTICLES
     * =====================================
     */

    private List<Article> findRelevantArticles(
            String question
    ) {


        /*
         * =====================================
         * PRIORITY 1:
         * DIRECT ARTICLE NUMBER
         * =====================================
         */

        Pattern pattern =

                Pattern.compile(

                        "\\barticle\\s+(\\d+[a-zA-Z]?)\\b",

                        Pattern.CASE_INSENSITIVE

                );


        Matcher matcher =
                pattern.matcher(
                        question
                );


        List<Article> directArticles =
                new ArrayList<>();


        while (
                matcher.find()
        ) {


            String articleNumber =

                    "Article "

                            +

                    matcher.group(1);


            Optional<Article> article =

                    articleRepository
                            .findByArticleNumber(
                                    articleNumber
                            );


            if (

                    article.isPresent()

                            &&

                    article.get()
                            .isPublished()

                            &&

                    !directArticles.contains(
                            article.get()
                    )

            ) {

                directArticles.add(
                        article.get()
                );
            }
        }


        if (
                !directArticles.isEmpty()
        ) {

            return directArticles;
        }


        /*
         * =====================================
         * PRIORITY 2:
         * KEYWORD MATCHING
         * =====================================
         */

        String normalizedQuestion =

                question
                        .toLowerCase()
                        .replaceAll(
                                "[^a-zA-Z0-9 ]",
                                " "
                        )
                        .trim();


        Set<String> questionWords =

                new HashSet<>(

                        Arrays.asList(

                                normalizedQuestion
                                        .split("\\s+")

                        )

                );


        questionWords.removeAll(

                Arrays.asList(

                        "what",

                        "why",

                        "how",

                        "when",

                        "where",

                        "who",

                        "is",

                        "are",

                        "was",

                        "were",

                        "the",

                        "a",

                        "an",

                        "and",

                        "or",

                        "of",

                        "to",

                        "in",

                        "on",

                        "explain",

                        "tell",

                        "give",

                        "about",

                        "me",

                        "my",

                        "you",

                        "this",

                        "that",

                        "it",

                        "short",

                        "shorter",

                        "simple",

                        "simpler",

                        "example",

                        "india",

                        "indian",

                        "constitution",

                        "article"

                )

        );


        if (
                questionWords.isEmpty()
        ) {

            return new ArrayList<>();
        }


        /*
         * Get published articles.
         */

        List<Article> allArticles =

                articleRepository
                        .findByPublishedTrue(

                                PageRequest.of(
                                        0,
                                        100
                                )

                        )
                        .getContent();


        Map<Article, Integer> scores =
                new HashMap<>();


        for (
                Article article : allArticles
        ) {


            int score = 0;


            String title =
                    safeLower(
                            article.getTitle()
                    );


            String keywords =
                    safeLower(
                            article.getKeywords()
                    );


            String explanation =
                    safeLower(
                            article.getSimplifiedExplanation()
                    );


            for (
                    String word : questionWords
            ) {


                if (
                        word.length() < 3
                ) {

                    continue;
                }


                if (
                        title.contains(word)
                ) {

                    score += 10;
                }


                if (
                        keywords.contains(word)
                ) {

                    score += 8;
                }


                if (
                        explanation.contains(word)
                ) {

                    score += 2;
                }
            }


            if (
                    score > 0
            ) {

                scores.put(
                        article,
                        score
                );
            }
        }


        /*
         * Only top 2 articles.
         */

        return scores
                .entrySet()
                .stream()
                .sorted(

                        Map.Entry.comparingByValue(
                                Comparator.reverseOrder()
                        )

                )
                .limit(2)
                .map(
                        Map.Entry::getKey
                )
                .toList();
    }


    /*
     * =====================================
     * SAFE LOWER
     * =====================================
     */

    private String safeLower(
            String value
    ) {

        return value == null

                ?

                ""

                :

                value.toLowerCase();
    }


    /*
     * =====================================
     * BUILD CONSTITUTION CONTEXT
     * =====================================
     */

    private String buildConstitutionContext(
            List<Article> articles
    ) {


        if (

                articles == null

                        ||

                articles.isEmpty()

        ) {

            return "No relevant local article context.";
        }


        StringBuilder context =
                new StringBuilder();


        for (
                Article article : articles
        ) {


            context
                    .append("Article: ")
                    .append(
                            article.getArticleNumber()
                    )
                    .append("\n");


            context
                    .append("Title: ")
                    .append(
                            article.getTitle()
                    )
                    .append("\n");


            if (
                    article.getSimplifiedExplanation() != null
            ) {

                context
                        .append("Explanation: ")
                        .append(
                                article.getSimplifiedExplanation()
                        )
                        .append("\n");
            }


            /*
             * Avoid sending very large
             * constitutional text.
             */

            if (

                    article.getConstitutionalText() != null

                            &&

                    article.getConstitutionalText().length() < 2500

            ) {

                context
                        .append("Text: ")
                        .append(
                                article.getConstitutionalText()
                        )
                        .append("\n");
            }


            context.append("\n");
        }


        return context.toString();
    }


    /*
     * =====================================
     * BUILD AI PROMPT
     * =====================================
     */

    private String buildPrompt(

            String question,

            String constitutionContext,

            String conversationHistory,

            MessageIntent intent

    ) {


        return """

                You are Constitution AI, a professional, polite, friendly,
                and intelligent AI assistant.

                You specialize in the Indian Constitution and civics, but
                you can also answer general questions naturally.

                RULES:

                - Answer the user's question directly.
                - Be concise by default.
                - Do not write long answers unless the user asks for
                  detailed information.
                - Prefer 2-5 short paragraphs or concise bullet points.
                - For simple questions, answer in a few sentences.
                - Use conversation history to understand follow-up questions.
                - "shorter" means shorten the previous answer.
                - "explain more" means add useful detail.
                - "give an example" means provide an example related to
                  the previous topic.
                - Do not unnecessarily repeat previous information.
                - Do not use robotic phrases.
                - Do not mention databases, prompts, internal systems,
                  or implementation details.
                - Answer general questions normally.
                - When local Constitution context is relevant, prioritize it.
                - Do not invent important constitutional facts.
                - If uncertain about an important fact, clearly say so.
                - Reply naturally in the user's language and style.
                - Do not repeatedly ask "Anything else?"

                INTENT:
                """
                + intent
                + """

                RECENT CONVERSATION:
                """
                + conversationHistory
                + """

                CONSTITUTION CONTEXT:
                """
                + constitutionContext
                + """

                USER QUESTION:
                """
                + question
                + """

                Give a direct, helpful and concise answer.
                """;
    }


    /*
     * =====================================
     * CALL GROQ
     * =====================================
     */

    private String askGroq(

            String prompt,

            MessageIntent intent

    ) {


        /*
         * =====================================
         * REQUEST BODY
         * =====================================
         */

        Map<String, Object> requestBody =
                new HashMap<>();


        /*
         * MODEL
         */

        requestBody.put(
                "model",
                groqModel
        );


        /*
         * =====================================
         * CHAT MESSAGES
         * =====================================
         */

        List<Map<String, String>> messages =
                new ArrayList<>();


        Map<String, String> userMessage =
                new HashMap<>();


        userMessage.put(
                "role",
                "user"
        );


        userMessage.put(
                "content",
                prompt
        );


        messages.add(
                userMessage
        );


        requestBody.put(
                "messages",
                messages
        );


        /*
         * =====================================
         * NON-STREAMING RESPONSE
         * =====================================
         */

        requestBody.put(
                "stream",
                false
        );


        /*
         * =====================================
         * TEMPERATURE
         * =====================================
         */

        requestBody.put(
                "temperature",
                0.3
        );


        /*
         * =====================================
         * MAX TOKENS
         * =====================================
         */

        int maxTokens = 220;


        if (

                intent == MessageIntent.RESPONSE_MODIFICATION

                        ||

                intent == MessageIntent.FOLLOW_UP

        ) {

            maxTokens = 180;
        }


        requestBody.put(
                "max_tokens",
                maxTokens
        );


        /*
         * =====================================
         * HEADERS
         * =====================================
         */

        HttpHeaders headers =
                new HttpHeaders();


        headers.setContentType(
                MediaType.APPLICATION_JSON
        );


        headers.setBearerAuth(
                groqApiKey
        );


        /*
         * =====================================
         * HTTP REQUEST
         * =====================================
         */

        HttpEntity<Map<String, Object>> request =

                new HttpEntity<>(

                        requestBody,

                        headers

                );


        /*
         * =====================================
         * CALL GROQ API
         * =====================================
         */

        try {


            Map response =

                    restTemplate.exchange(

                            groqApiUrl,

                            HttpMethod.POST,

                            request,

                            Map.class

                    ).getBody();


            /*
             * =====================================
             * READ GROQ RESPONSE
             *
             * Expected:
             *
             * choices[0]
             *   -> message
             *      -> content
             * =====================================
             */

            if (

                    response != null

                            &&

                    response.get("choices") instanceof List

            ) {


                List choices =

                        (List)
                                response.get(
                                        "choices"
                                );


                if (
                        !choices.isEmpty()
                ) {


                    Map firstChoice =

                            (Map)
                                    choices.get(0);


                    Object messageObject =

                            firstChoice.get(
                                    "message"
                            );


                    if (

                            messageObject instanceof Map

                    ) {


                        Map message =

                                (Map)
                                        messageObject;


                        Object content =

                                message.get(
                                        "content"
                                );


                        if (

                                content != null

                                        &&

                                !content
                                        .toString()
                                        .trim()
                                        .isEmpty()

                        ) {


                            return content
                                    .toString()
                                    .trim();
                        }
                    }
                }
            }


            /*
             * =====================================
             * EMPTY RESPONSE
             * =====================================
             */

            return "I couldn't generate a response right now. Please try again.";


        }

        catch (

                Exception error

        ) {


            /*
             * =====================================
             * SERVER LOG
             * =====================================
             */

            error.printStackTrace();


            /*
             * =====================================
             * USER RESPONSE
             * =====================================
             */

            return """
                    I'm unable to connect to the AI service right now.
                    Please try again in a moment.
                    """;
        }
    }


    /*
     * =====================================
     * CONSTITUTION QUESTION DETECTION
     * =====================================
     */

    private boolean isConstitutionQuestion(
            String question
    ) {


        String lowerQuestion =
                question.toLowerCase();


        List<String> keywords =

                Arrays.asList(

                        "constitution",

                        "article",

                        "fundamental right",

                        "fundamental rights",

                        "right to equality",

                        "equality before law",

                        "discrimination",

                        "freedom of speech",

                        "freedom of expression",

                        "personal liberty",

                        "right to life",

                        "right to education",

                        "directive principles",

                        "fundamental duties",

                        "supreme court",

                        "constitutional amendment",

                        "preamble",

                        "parliament",

                        "lok sabha",

                        "rajya sabha",

                        "judiciary",

                        "president of india",

                        "prime minister",

                        "emergency provisions",

                        "constituent assembly",

                        "ambedkar",

                        "schedule",

                        "amendment"

                );


        return keywords.stream()
                .anyMatch(
                        lowerQuestion::contains
                );
    }

}