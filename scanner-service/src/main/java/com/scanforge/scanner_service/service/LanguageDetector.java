package com.scanforge.scanner_service.service;

import com.scanforge.scanner_service.model.Language;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class LanguageDetector {

    public Language detect(File file){
        String name = file.getName().toLowerCase();
        if(name.endsWith(".java")) return Language.JAVA;
        if(name.endsWith(".py")) return Language.PYTHON;
        if(name.endsWith(".js")) return Language.JAVASCRIPT;
        if(name.endsWith(".ts")) return Language.TYPESCRIPT;
        if(name.endsWith(".go")) return Language.GO;
        return null;
    }
}
