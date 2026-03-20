package com.scanforge.ai_service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scanforge.contracts.events.ScanDetectedEvent;
import com.scanforge.contracts.events.ScanEnrichedEvent;
import com.scanforge.contracts.models.VulnerabilityEnriched;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EnrichmentUtils {
    public static Map<String, String> fallback(){
        return Map.of(
                "explanation", "AI unavailable",
                "attack_example", "",
                "secure_fix", ""
        );
    }

    public static ScanEnrichedEvent empty(ScanDetectedEvent e){
        return ScanEnrichedEvent
                .builder()
                .scanId(e.getScanId())
                .score(100)
                .issues(List.of())
                .processedAt(LocalDateTime.now())
                .build();
    }

    public static List<Map<String, String>> parseList(String response){
        try{
            String cleaned = response
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            ObjectMapper mapper = new ObjectMapper();

            return mapper
                    .readValue(
                            cleaned,
                            new TypeReference<>() {
                            }
                    );
        }
        catch (Exception e){
            return List.of();
        }
    }

    public static int calculateScore(List<VulnerabilityEnriched> issues){
        int score = 100;
        for(var v : issues){
            switch (v.getSeverity()){
                case "Critical" -> score -= 20;
                case "High" -> score -= 10;
                case "Medium" -> score -= 5;
                case "Low" -> score -= 2;
            }
        }

        return Math.max(score, 0);
    }
}
