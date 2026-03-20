package com.scanforge.scanner_service.rules;

import com.scanforge.scanner_service.model.Rule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RuleCacheService {
    private final RuleRepository repository;
    private Map<String, List<Rule>> rulesByLanguage;

    @PostConstruct
    public void loadRules(){
        List<Rule> rules = repository.findAll();

        System.out.println(rules);
        for (Rule r : rules){
            r.setCompiledPattern(Pattern.compile(r.getPattern()));
        }

        this.rulesByLanguage = rules
                .stream()
                .collect(Collectors.groupingBy(Rule::getFileType));
    }

    @Scheduled(fixedRate = 50000)
    public void refreshRules(){
        loadRules();
        System.out.println("Rules Refreshed");
    }
    public List<Rule> getRulesByLanguage(String language){
        return rulesByLanguage.getOrDefault(language, List.of());
    }

}
