package com.scanforge.scanner_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.regex.Pattern;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "rules")
public class Rule {
    @Id
    private String id;
    private String name;
    private String severity;
    private String cwe;
    private String description;
    private String fileType;
    private String pattern;
    @Transient
    private Pattern compiledPattern;
}
