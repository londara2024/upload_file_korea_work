package com.tosrean.tosrean.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ApiBaseResponse <T> {
    private String message;
    private T data;
    private HttpStatus status;
    private String date;

    public ApiBaseResponse() {
        setDateTime();
    }

    private void setDateTime() {
        // create an LocalDateTime object
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        setDate(now.format(format));
    }
}
