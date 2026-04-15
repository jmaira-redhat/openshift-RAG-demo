package com.example.water;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/water")
/**
 * The @CrossOrigin annotation is essential in a DevSpace/OpenShift environment.
 * It allows your React/Next.js frontend (usually running on port 3000) 
 * to securely call this Java API (running on port 8080).
 */
@CrossOrigin(origins = "*") 
public class WaterController {

    private static final Logger log = LoggerFactory.getLogger(WaterController.class);
    private final AssistantApp.WaterExpert expert;

    /**
     * Spring Boot automatically finds the 'WaterExpert' Bean we defined 
     * in WaterApplication.java and injects it here.
     */
    public WaterController(AssistantApp.WaterExpert expert) {
        this.expert = expert;
    }

    /**
     * This endpoint receives the user's question, sends it to the RAG service,
     * and returns the AI's grounded response.
     */
    @PostMapping("/chat")
    public ChatResponse ask(@RequestBody ChatRequest request) {
        log.info("Received water billing inquiry: {}", request.message());
        
        try {
            // This calls the LangChain4j RAG pipeline
            String answer = expert.answer(request.message());
            return new ChatResponse(answer);
        } catch (Exception e) {
            log.error("Error processing RAG request", e);
            return new ChatResponse("I'm sorry, I'm having trouble connecting to the water knowledge base. Please try again later.");
        }
    }

    /**
     * Using Java Records (introduced as standard in Java 17) for clean, 
     * immutable Data Transfer Objects (DTOs).
     */
    public record ChatRequest(String message) {}
    public record ChatResponse(String answer) {}
}