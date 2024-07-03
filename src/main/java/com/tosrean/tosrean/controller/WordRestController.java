package com.tosrean.tosrean.controller;

import com.tosrean.tosrean.service.WordService;
import com.tosrean.tosrean.utils.ApiBaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class WordRestController {
    private final WordService wordService;

    @PostMapping(value = "/uploadExcel", consumes = {
            "multipart/form-data"
    })
    @Operation(summary = "Upload multiple Files")
    public ResponseEntity<?> postWord(@RequestParam("file") MultipartFile file) {
        ApiBaseResponse wordResponse = new ApiBaseResponse();
        Map<Integer, String> errorMassage = wordService.postWord(file);
        wordResponse.setData(errorMassage);
        wordResponse.setStatus(HttpStatus.OK);
        wordResponse.setMessage("success");
        return ResponseEntity.ok(wordResponse);
    }

    @PostMapping(value = "/uploadPDF", consumes = {
            "multipart/form-data"
    })
    @Operation(summary = "Upload multiple Files")
    public ResponseEntity<?> postPDFWord(@RequestParam("file") MultipartFile file) {
        ApiBaseResponse wordResponse = new ApiBaseResponse();
        wordService.postPFD(file);
        wordResponse.setStatus(HttpStatus.OK);
        wordResponse.setMessage("success");
        return ResponseEntity.ok(wordResponse);
    }

    @PostMapping(value = "/korean_and_khmer/uploadPDF", consumes = {
            "multipart/form-data"
    })
    @Operation(summary = "Upload multiple Files")
    public ResponseEntity<?> postKorenAndKhmerPDFWord(@RequestParam("file") MultipartFile file) {
        ApiBaseResponse wordResponse = new ApiBaseResponse();
        wordService.postKoreanKhmer(file);
        wordResponse.setStatus(HttpStatus.OK);
        wordResponse.setMessage("success");
        return ResponseEntity.ok(wordResponse);
    }

}
