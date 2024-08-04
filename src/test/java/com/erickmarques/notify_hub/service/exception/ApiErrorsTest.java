package com.erickmarques.notify_hub.service.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link ApiErrors}.
 */
class ApiErrorsTest {

    private final String TIME_STAMP = LocalDateTime.now().toString();
    private final String PATH = "/api/test";
    private final String MESSAGE = "Teste mensagem exception!";

    @Test
    void testApiErrorsConstructorWithSingleMessage() {
        // Act
        ApiErrors apiErrors = new ApiErrors(TIME_STAMP, PATH, MESSAGE);

        // Assert
        assertThat(apiErrors.getTimestamp()).isEqualTo(TIME_STAMP);
        assertThat(apiErrors.getPath()).isEqualTo(PATH);
        assertThat(apiErrors.getErrors()).containsExactly(MESSAGE);
    }

    @Test
    void testApiErrorsConstructorWithErrorList() {
        // Arrange
        var messageSecond = "Teste mensagem exception 2!";
        var errors = Arrays.asList(MESSAGE, messageSecond);

        // Act
        ApiErrors apiErrors = new ApiErrors(TIME_STAMP, PATH, errors);

        // Assert
        assertThat(apiErrors.getTimestamp()).isEqualTo(TIME_STAMP);
        assertThat(apiErrors.getPath()).isEqualTo(PATH);
        assertThat(apiErrors.getErrors()).containsExactly(MESSAGE, messageSecond);
    }
}