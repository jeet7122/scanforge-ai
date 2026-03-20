package com.scanforge.api_service.util;

import com.scanforge.api_service.exception.InvalidFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {
    private static final String FILE_EXTENSION = ".zip";

    public void validate(MultipartFile file){
        if (file.isEmpty()){
            throw new InvalidFileException("Uploaded file is empty");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(FILE_EXTENSION)){
            throw new InvalidFileException("Only ZIP files are allowed");
        }
    }
}
