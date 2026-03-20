package com.scanforge.result_service.kafka;

import com.scanforge.contracts.events.ScanEnrichedEvent;
import com.scanforge.result_service.service.ScanResultService;
import com.scanforge.result_service.websockets.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScanEnrichedConsumer {

    private final ScanResultService resultService;
    private final WebSocketPublisher publisher;
    private final ScanStatusProducer scanStatusProducer;

    @KafkaListener(topics = "scan-enriched", groupId = "result-group")
    public void consume(ScanEnrichedEvent e){
        log.info("Received scan-enriched: {}", e.getScanId());

        try{
            resultService.save(e);
            scanStatusProducer.send(e.getScanId(), "SAVED");
            publisher.publishResult(e);
        }
        catch (Exception ex){
            log.error("Failed processing scan {}", e.getScanId(), ex);
        }
    }
}
