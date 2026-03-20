package com.scanforge.api_service.controller;

import com.scanforge.api_service.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scan")
@CrossOrigin("http://localhost:3000")
public class ScanController {

    private final ScanService scanService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadZip(@RequestParam MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error", "File is missing"));
        }
        String scanId = scanService.startScan(file);
        return ResponseEntity.ok(Map.of("scanId", scanId));
    }

}
