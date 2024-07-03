package com.tosrean.tosrean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {
    private String wordKoren;
    private String partOfSpeech;
    private String translateKhmer;
    private String translateEnglish;
}
