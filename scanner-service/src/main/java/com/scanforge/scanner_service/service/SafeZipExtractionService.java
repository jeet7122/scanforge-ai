package com.scanforge.scanner_service.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class SafeZipExtractionService {
    // 100 MB MAX
    private static final long MAX_TOTAL_SIZE = 100 * 1024 * 1024;
    // 10 MB MAX
    private static final long MAZ_FILE_SIZE = 10 * 1024 * 1024;
    private static final int MAX_NUMBER_OF_FILES = 1000;


    public List<File> extract(String zipPath){
        List<File> extractedFiles = new ArrayList<>();

        Path destinationDir = Paths.get("extracted/" + UUID.randomUUID());
        long totalExtractedSize = 0;
        int fileCount = 0;

        try{
            Files.createDirectories(destinationDir);
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null){
                fileCount++;

                if (fileCount > MAX_NUMBER_OF_FILES) throw new RuntimeException("Too many files in zip");

                Path target = destinationDir.resolve(entry.getName()).normalize();

                // Zip Slip Protection
                if (!target.startsWith(destinationDir)) {
                    throw new RuntimeException("Zip Slip attack detected");
                }

                if (entry.isDirectory()){
                    Files.createDirectories(target);
                    continue;
                }

                Files.createDirectories(target.getParent());

                long extractedFileSize = 0;

                try (OutputStream os = Files.newOutputStream(target)) {
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        extractedFileSize += len;
                        totalExtractedSize += len;

                        if (extractedFileSize > MAZ_FILE_SIZE) throw new RuntimeException("File too large");
                        if (totalExtractedSize > MAX_TOTAL_SIZE) throw new RuntimeException("Zip too large");

                        os.write(buffer, 0, len);
                    }
                }
                extractedFiles.add(target.toFile());
            }

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return extractedFiles;
    }
}
