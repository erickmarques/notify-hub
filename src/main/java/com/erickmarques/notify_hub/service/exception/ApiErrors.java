package com.erickmarques.notify_hub.service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class ApiErrors {
    private String timestamp;
    private String path;
    private List<String> errors;
}