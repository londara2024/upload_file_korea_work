package com.tosrean.tosrean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KoreanAndKhmerDTO {
    private String wordKoren;
    private String wordKhmer;
}
