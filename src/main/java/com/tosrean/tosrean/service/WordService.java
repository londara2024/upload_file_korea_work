package com.tosrean.tosrean.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface WordService {
    Map<Integer, String> postWord(MultipartFile file);
    void postPFD (MultipartFile file);
    void postKoreanKhmer (MultipartFile file);
}
