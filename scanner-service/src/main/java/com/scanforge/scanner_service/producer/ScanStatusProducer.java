package com.scanforge.scanner_service.producer;

import com.scanforge.contracts.events.ScanStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScanStatusProducer {
    private final KafkaTemplate<String, ScanStatusEvent> kafkaTemplate;

    public void send(String scanId, String status){
        ScanStatusEvent e = ScanStatusEvent.builder()
                .scanId(scanId)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(
                "scan-status",
                scanId,
                e
        );
    }
}
