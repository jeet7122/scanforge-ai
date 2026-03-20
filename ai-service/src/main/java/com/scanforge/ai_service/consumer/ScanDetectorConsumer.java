package com.scanforge.ai_service.consumer;

import com.scanforge.ai_service.producer.ScanEnrichedProducer;
import com.scanforge.ai_service.producer.ScanStatusProducer;
import com.scanforge.ai_service.service.AiEnrichmentService;
import com.scanforge.contracts.events.ScanDetectedEvent;
import com.scanforge.contracts.events.ScanEnrichedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ScanDetectorConsumer {
    private final AiEnrichmentService aiEnrichmentService;
    private final ScanEnrichedProducer producer;
    private final ScanStatusProducer scanStatusProducer;

    @KafkaListener(topics = "scan-detected", groupId = "ai-group")
    public void consume(ScanDetectedEvent e){
        log.info("Received scan-detected event: {}", e.getScanId());

        try{
            String scanId = e.getScanId();
            scanStatusProducer.send(scanId, "AI_PROCESSING");
            ScanEnrichedEvent se = aiEnrichmentService.enrich(e);
            producer.send(se);
        }
        catch (Exception ex){
            log.error("Failed to process scanId={}",e.getScanId() ,ex);
        }
    }
}
