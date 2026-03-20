package com.scanforge.result_service.model;

import com.scanforge.contracts.models.VulnerabilityEnriched;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "scans")
public class Scan {
    @Id
    private String scanId;
    private int score;
    private List<VulnerabilityEnriched> issues;
    private LocalDateTime createdAt;
}
