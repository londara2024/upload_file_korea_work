package com.tosrean.tosrean.entity;

import com.tosrean.tosrean.dto.WordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "word")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String wordKoren;
    private String partOfSpeech;
    private String translateKhmer;
    private String translateEnglish;

    public Word(WordDTO wordDTO) {
        this.wordKoren = wordDTO.getWordKoren();
        this.partOfSpeech = wordDTO.getPartOfSpeech();
        this.translateKhmer = wordDTO.getTranslateKhmer();
        this.translateEnglish = wordDTO.getTranslateEnglish();
    }
}
