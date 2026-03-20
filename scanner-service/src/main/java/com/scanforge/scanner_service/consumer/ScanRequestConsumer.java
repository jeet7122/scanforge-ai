package com.scanforge.scanner_service.consumer;

import com.scanforge.contracts.events.ScanDetectedEvent;
import com.scanforge.contracts.events.ScanRequestEvent;
import com.scanforge.contracts.models.VulnerabilityDetected;
import com.scanforge.scanner_service.producer.ScanDetectorProducer;
import com.scanforge.scanner_service.producer.ScanStatusProducer;
import com.scanforge.scanner_service.service.FileFilterService;
import com.scanforge.scanner_service.service.RuleEngineService;
import com.scanforge.scanner_service.service.SafeZipExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScanRequestConsumer {

    private final SafeZipExtractionService zipExtractionService;
    private final RuleEngineService ruleEngineService;
    private final ScanDetectorProducer producer;
    private final FileFilterService fileFilterService;
    private final ScanStatusProducer scanStatusProducer;

    @KafkaListener(topics = "scan-request", groupId = "scanner-group")
    public void consume(ScanRequestEvent event){
        log.info("Received scan-request:{}", event.getScanId());
        String scanId = event.getScanId();
        scanStatusProducer.send(scanId, "EXTRACTING");
        List<File> files = zipExtractionService.extract(event.getFilePath());
        scanStatusProducer.send(scanId, "SCANNING");
        List<File> supportedFiles = fileFilterService.filterSupportedFiles(files);
        List<VulnerabilityDetected> issues = ruleEngineService.scanFile(supportedFiles);
        System.out.println("Issues Detected: " + issues);

        ScanDetectedEvent detectedEvent = ScanDetectedEvent.builder()
                .scanId(event.getScanId())
                .issues(issues)
                .detectedAt(LocalDateTime.now())
                .build();
        producer.send(detectedEvent);
    }
}
