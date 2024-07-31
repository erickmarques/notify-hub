package com.erickmarques.notify_hub.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrors {
    private String timestamp;
    private String path;
    private List<String> errors;

    public ApiErrors(String timestamp, String path, String msg){
        this.timestamp = timestamp;
        this.path      = path;
        this.errors    = Arrays.asList(msg);
    }
}