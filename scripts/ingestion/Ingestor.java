package com.example.water;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;

public class Ingestor {
    public static void main(String[] args) {
        // 1. Setup the Store
        PgVectorEmbeddingStore store = PgVectorEmbeddingStore.builder()
            .host("water-db-local")
            .port(5432)
            .database("water_billing")
            .user("wateradmin")
            .password("calif-water-pass-2026")
            .table("water_knowledge")
            .dimension(384) 
            .build();

        // 2. Setup the Embedding Model
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        // 3. The California Water Knowledge (CLEANED)
        String[] rules = {
            "2026 California Tiered Rates: Tier 1 (Indoor) is $6.50/CCF for 0-7 units. Tier 2 is $9.20/CCF for 8-25 units.",
            "Stage 2 Drought Alert Rule: Usage over 25 units (Tier 3) incurs a 15% surcharge in 2026.",
            "The California Arrearage Payment Program (CAPP) provides credits for residential water customers with past-due bills.",
            "SFPUC 2026 Policy: Water service cannot be shut off for non-payment if the balance is under $200 and a payment plan is active."
        };

        System.out.println("Starting ingestion into OpenShift Postgres...");
        
        for (String rule : rules) {
            TextSegment segment = TextSegment.from(rule);
            store.add(embeddingModel.embed(segment).content(), segment);
        }
        
        System.out.println("Success: Water Knowledge Base Populated!");
    }
}