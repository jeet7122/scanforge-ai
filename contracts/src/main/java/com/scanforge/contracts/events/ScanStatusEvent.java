package com.scanforge.contracts.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScanStatusEvent {
    private String scanId;
    private String status;
    private LocalDateTime timestamp;
}
