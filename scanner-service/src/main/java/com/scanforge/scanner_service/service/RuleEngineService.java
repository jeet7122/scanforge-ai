package com.scanforge.scanner_service.service;

import com.scanforge.contracts.models.VulnerabilityDetected;
import com.scanforge.scanner_service.model.Language;
import com.scanforge.scanner_service.model.Rule;
import com.scanforge.scanner_service.rules.RuleCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class RuleEngineService {
    private final RuleCacheService ruleCacheService;
    private final SnippetExtractor snippetExtractor;
    private final LanguageDetector languageDetector;

    public List<VulnerabilityDetected> scanFile(List<File> files){
        List<VulnerabilityDetected> issues = new ArrayList<>();
        for (File file : files){
            Language language = languageDetector.detect(file);
            if(language == null) {
                System.out.println("No Language");
                continue;
            }
            List<Rule> rules = ruleCacheService.getRulesByLanguage(language.name().toLowerCase());
            System.out.println("Rules By Language Loaded: " + rules);
            if(rules == null) continue;
            String code = readFile(file);
            for (Rule r : rules){
                Matcher matcher = r.getCompiledPattern().matcher(code);
                while (matcher.find()){
                    System.out.println("Found Matching snippet");
                    String snippet = snippetExtractor.extractSnippet(code, matcher.start());
                    issues.add(VulnerabilityDetected.builder()
                                    .ruleId(r.getId())
                                    .filePath(file.getPath())
                                    .type(r.getName())
                                    .severity(r.getSeverity())
                                    .snippet(snippet)
                            .build());
                }
            }
        }
        return issues;
    }

    private String readFile(File f){
        try {
            return Files.readString(f.toPath());
        }
        catch (Exception e){
            return "";
        }
    }
}
