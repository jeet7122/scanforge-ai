package com.scanforge.scanner_service.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ZipExtractionService {
    public List<File> extract(String zipPath){
        List<File> extractedFiles = new ArrayList<>();
        Path destinationDir = Paths.get("extracted/" + UUID.randomUUID());
        try{
            Files.createDirectories(destinationDir);
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                Path target = destinationDir.resolve(entry.getName()).normalize();
                if (!target.startsWith(destinationDir)){
                    throw new RuntimeException("Zip Slip Detected");
                }

                File newFile = target.toFile();

                if (entry.isDirectory()){
                    newFile.mkdirs();
                }
                else {
                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);
                    zis.transferTo(fos);
                    fos.close();
                    extractedFiles.add(newFile);
                }
            }
            zis.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        return extractedFiles;
    }
}
