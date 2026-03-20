package com.scanforge.result_service.service;

import com.scanforge.contracts.events.ScanEnrichedEvent;
import com.scanforge.result_service.model.Scan;
import com.scanforge.result_service.repository.ScanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScanResultService {
    private final ScanRepository scanRepository;

    public void save(ScanEnrichedEvent e){
        Scan scan = Scan
                .builder()
                .scanId(e.getScanId())
                .score(e.getScore())
                .issues(e.getIssues())
                .createdAt(e.getProcessedAt())
                .build();

        scanRepository.save(scan);

        log.info("Scan saved to repo, SCAN_ID: {}", scan.getScanId());

    }
}
