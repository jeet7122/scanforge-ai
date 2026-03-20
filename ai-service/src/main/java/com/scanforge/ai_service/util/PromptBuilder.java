package com.scanforge.ai_service.util;

import com.scanforge.contracts.models.VulnerabilityDetected;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    public String build(List<VulnerabilityDetected> issues){
        StringBuilder input = new StringBuilder();

        for (int i = 0; i < issues.size(); i++) {
            VulnerabilityDetected v = issues.get(i);

            input.append("""
                    {
                        "id": "%d",
                        "type": "%s",
                        "code": "%s"
                    }
                    """.formatted(i, v.getType(), v.getSnippet()));
        }
        return """
        You are a security expert.

        Analyze the following vulnerabilities.

        INPUT:
        [
        %s
        ]

        IMPORTANT:
        - Return ONLY raw JSON
        - No markdown
        - No backticks

        OUTPUT FORMAT:
        [
          {
            "id": "0",
            "explanation": "...",
            "attack_example": "...",
            "secure_fix": "..."
          }
        ]
        """.formatted(input.toString());

    }
}
