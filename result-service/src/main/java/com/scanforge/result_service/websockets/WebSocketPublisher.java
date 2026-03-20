package com.scanforge.result_service.websockets;

import com.scanforge.contracts.events.ScanEnrichedEvent;
import com.scanforge.contracts.events.ScanStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebSocketPublisher {
    private final SimpMessagingTemplate messagingTemplate;

    public void publishResult(ScanEnrichedEvent e){
        Map<String, Object> payload = Map
                .of(
                        "type", "RESULT",
                        "data", e
                );
        messagingTemplate
                .convertAndSend(
                        "/topic/scan/" + e.getScanId(),
                        payload
                );
    }

    public void publishStatus(ScanStatusEvent e){
        Map<String, Object> payload = Map.of(
                "type", "STATUS",
                "data", e
        );
        messagingTemplate
                .convertAndSend("/topic/scan/" + e.getScanId(), payload);
    }
}
