package com.scanforge.api_service.kafka;

import com.scanforge.contracts.events.ScanRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScanProducer {
    private final KafkaTemplate<String, ScanRequestEvent> kafkaTemplate;

    public void sendScanRequest(ScanRequestEvent e){
        kafkaTemplate.send(
                "scan-request",
                e.getScanId(),
                e
        );
    }
}
