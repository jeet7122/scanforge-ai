package com.scanforge.contracts.events;

import com.scanforge.contracts.models.VulnerabilityEnriched;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanEnrichedEvent {
    private String scanId;
    private int score;
    private List<VulnerabilityEnriched> issues;
    private LocalDateTime processedAt;
}
