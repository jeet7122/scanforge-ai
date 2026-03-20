package com.scanforge.result_service.websockets;


import com.scanforge.contracts.events.ScanStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScanStatusConsumer {
    private final WebSocketPublisher publisher;

    @KafkaListener(topics = "scan-status", groupId = "result-group")
    public void consume(ScanStatusEvent e){
        publisher.publishStatus(e);
    }
}
