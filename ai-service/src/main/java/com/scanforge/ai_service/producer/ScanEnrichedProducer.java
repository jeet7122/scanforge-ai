package com.scanforge.ai_service.producer;

import com.scanforge.contracts.events.ScanEnrichedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScanEnrichedProducer {
    private final KafkaTemplate<String, ScanEnrichedEvent> kafkaTemplate;

    public void send(ScanEnrichedEvent e){
        kafkaTemplate.send(
                "scan-enriched",
                e.getScanId(),
                e
        );
        log.info("Sent scan-enriched event: {}", e.getScanId());
    }
}
