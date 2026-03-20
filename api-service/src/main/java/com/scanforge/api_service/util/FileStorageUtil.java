package com.scanforge.api_service.util;

import com.scanforge.api_service.exception.FileStorageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStorageUtil {

    private final String uploadDir = "/app/uploads";

    public String saveFile(MultipartFile file){

        try{

            File dir = new File(uploadDir);

            if (!dir.exists()){
                dir.mkdirs();
            }

            String originalName = file.getOriginalFilename() != null
                    ? file.getOriginalFilename()
                    : "file.zip";

            String filePath =
                    uploadDir + "/" +
                            UUID.randomUUID() + "_" +
                            originalName;

            File destination = new File(filePath);

            file.transferTo(destination);

            return filePath;

        } catch (IOException e){

            throw new FileStorageException(
                    "Failed to store uploaded file",
                    e
            );
        }
    }
}
