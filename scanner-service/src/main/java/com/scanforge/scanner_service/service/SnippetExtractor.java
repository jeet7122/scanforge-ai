package com.scanforge.scanner_service.service;

import org.springframework.stereotype.Service;

@Service
public class SnippetExtractor {
    public String extractSnippet(String code, int index){
        int start = Math.max(0, index - 120);
        int end = Math.min(code.length(), index + 120);
        return code.substring(start, end);
    }
}
