package com.scanforge.contracts.events;

import com.scanforge.contracts.models.VulnerabilityDetected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScanDetectedEvent {
    private String scanId;
    private List<VulnerabilityDetected> issues;
    private LocalDateTime detectedAt;
}
