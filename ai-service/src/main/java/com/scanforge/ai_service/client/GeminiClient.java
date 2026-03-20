package com.scanforge.ai_service.client;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Component;

@Component
public class GeminiClient {

    private final Client client = new Client();

    public String generate(String prompt){
        GenerateContentResponse response = client.models
                .generateContent(
                        "gemini-3-flash-preview",
                        prompt,
                        null
                );

        return response.text();
    }
}
