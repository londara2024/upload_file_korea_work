package com.tosrean.tosrean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkAndEnglishAndKhDTO {
    private String wordKoren;
    private String workEnglish;
    private String wordKhmer;
}
