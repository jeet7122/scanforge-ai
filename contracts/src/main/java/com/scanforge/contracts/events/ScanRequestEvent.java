package com.scanforge.contracts.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanRequestEvent {
    private String scanId;
    private String filePath;
    private LocalDateTime createdAt;
}
