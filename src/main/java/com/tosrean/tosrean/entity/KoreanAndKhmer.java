package com.tosrean.tosrean.entity;

import com.tosrean.tosrean.dto.KoreanAndKhmerDTO;
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
@Table(name = "korea_and_khmer")
public class KoreanAndKhmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long khId;
    private String wordKoren;
    private String wordKhmer;

    public KoreanAndKhmer(KoreanAndKhmerDTO koreanAndKhmerDTO) {
        this.wordKoren = koreanAndKhmerDTO.getWordKoren();
        this.wordKhmer = koreanAndKhmerDTO.getWordKhmer();
    }

}
