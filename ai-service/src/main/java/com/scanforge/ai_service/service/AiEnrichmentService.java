package com.scanforge.ai_service.service;

import com.scanforge.ai_service.client.GeminiClient;
import com.scanforge.ai_service.util.EnrichmentUtils;
import com.scanforge.ai_service.util.PromptBuilder;
import com.scanforge.contracts.events.ScanDetectedEvent;
import com.scanforge.contracts.events.ScanEnrichedEvent;
import com.scanforge.contracts.models.VulnerabilityDetected;
import com.scanforge.contracts.models.VulnerabilityEnriched;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AiEnrichmentService {
    private final GeminiClient client;
    private final PromptBuilder promptBuilder;

    public ScanEnrichedEvent enrich(ScanDetectedEvent e){
        List<VulnerabilityDetected> issues = e.getIssues();

        if (issues.isEmpty()) return EnrichmentUtils.empty(e);

        String prompt = promptBuilder.build(issues);

        String response = client.generate(prompt);

        List<Map<String, String>> aiResponse = EnrichmentUtils.parseList(response);

        List<VulnerabilityEnriched> enrichedList = new ArrayList<>();
        for (int i = 0; i < issues.size(); i++) {
            VulnerabilityDetected v = issues.get(i);

            Map<String, String> ai = i < aiResponse.size() ? aiResponse.get(i) : EnrichmentUtils.fallback();

            VulnerabilityEnriched ve = VulnerabilityEnriched
                    .builder()
                    .ruleId(v.getRuleId())
                    .type(v.getType())
                    .severity(v.getSeverity())
                    .snippet(v.getSnippet())
                    .filePath(v.getFilePath())
                    .lineNumber(v.getLineNumber())
                    .secureFix(ai.get("secure_fix"))
                    .attackExample(ai.get("attack_example"))
                    .explanation(ai.get("explanation"))
                    .build();
            enrichedList.add(ve);
        }
        return ScanEnrichedEvent.builder()
                .scanId(e.getScanId())
                .issues(enrichedList)
                .score(EnrichmentUtils.calculateScore(enrichedList))
                .processedAt(LocalDateTime.now())
                .build();
    }
}
