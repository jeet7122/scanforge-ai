package com.scanforge.scanner_service.producer;


import com.scanforge.contracts.events.ScanDetectedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScanDetectorProducer {
    private final KafkaTemplate<String, ScanDetectedEvent> kafkaTemplate;

    public void send(ScanDetectedEvent e){
        kafkaTemplate.send("scan-detected", e.getScanId(), e);
    }
}
