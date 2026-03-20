package com.scanforge.api_service.service;

import com.scanforge.api_service.kafka.ScanProducer;
import com.scanforge.api_service.util.FileStorageUtil;
import com.scanforge.api_service.util.FileValidator;
import com.scanforge.contracts.events.ScanRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScanService {
    private final ScanProducer scanProducer;
    private final FileStorageUtil fileStorageUtil;
    private final FileValidator fileValidator;

    public String startScan(MultipartFile file) {
        fileValidator.validate(file);
        String scanId = UUID.randomUUID().toString();
        String filePath = fileStorageUtil.saveFile(file);
        ScanRequestEvent event = ScanRequestEvent.builder()
                .scanId(scanId)
                .filePath(filePath)
                .createdAt(LocalDateTime.now())
                .build();

        scanProducer.sendScanRequest(event);
        return scanId;
    }
}
