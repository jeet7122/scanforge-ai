package com.scanforge.scanner_service.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Set;

@Service
public class FileFilterService {
    private static final Set<String> SUPPORTER_EXTENSIONS =
            Set.of("java", "py", "js", "ts", "go");

    public List<File> filterSupportedFiles(List<File> files){
        return files
                .stream()
                .filter(file -> hasSupportedExtension(file.getName()))
                .toList();
    }
    private boolean hasSupportedExtension(String f){
        int index = f.lastIndexOf(".");

        if (index == -1){
            return false;
        }
        String ext = f.substring(index + 1).toLowerCase();
        return SUPPORTER_EXTENSIONS.contains(ext);
    }
}
