package com.example.water;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;

public class AssistantApp {

    public interface WaterExpert {
        String answer(String question);
    }

    // public static void main(String[] args) {
    //     // 1. Model setup
//    String dbHost     = System.getenv().getOrDefault("DB_HOST", "water-db-service");
//     String dbPort     = System.getenv().getOrDefault("DB_PORT", "5432");
//     String dbName     = System.getenv().getOrDefault("DB_NAME", "water_billing");
//     String dbUser     = System.getenv().getOrDefault("DB_USER", "wateradmin");
//     String dbPass     = System.getenv().getOrDefault("DB_PASS", "password");
//     String dbTable    = System.getenv().getOrDefault("DB_TABLE", "water_knowledge");
    
//     // AI Model Configuration
//     String baseUrl    = System.getenv().getOrDefault("AI_BASE_URL", "http://localhost:8080/v1");
//     String apiKey     = System.getenv().getOrDefault("AI_API_KEY", "none");
//     String modelName  = System.getenv().getOrDefault("AI_MODEL_NAME", "llama-4-maverick-17b");

//     // 1. Model setup
//     OpenAiChatModel model = OpenAiChatModel.builder()
//         .baseUrl(baseUrl) 
//         .apiKey(apiKey)
//         .modelName(modelName)
//         .build();

//     // 2. Vector Store setup
//     PgVectorEmbeddingStore store = PgVectorEmbeddingStore.builder()
//         .host(dbHost)
//         .port(Integer.parseInt(dbPort))
//         .database(dbName)
//         .user(dbUser)
//         .password(dbPass)
//         .table(dbTable)
//         .dimension(384)
//         .build();
    //     // 3. RAG Retriever
    //     EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
    //         .embeddingStore(store)
    //         .embeddingModel(new AllMiniLmL6V2EmbeddingModel())
    //         .maxResults(2)
    //         .build();

    //     // 4. Create the Service (FIXED METHOD NAME FOR 2026)
    //     WaterExpert expert = AiServices.builder(WaterExpert.class)
    //         .chatModel(model) // Change from chatLanguageModel to chatModel
    //         .contentRetriever(retriever)
    //         .build();

    //     // 5. Test
    //     String response = expert.answer("I have a $300 water bill. Can you shut off my water?");
    //     System.out.println("\nUSER: I have a $300 water bill. Can you shut off my water?");
    //     System.out.println("\nLLAMA RESPONSE: " + response);
    // }
}