package com.herrywang.apibanking.exception;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ApiBankingExceptionHandlerTest {

    private final ApiBankingExceptionHandler exceptionHandler = new ApiBankingExceptionHandler();

    @Test
    void handleValidationException() {
        final ValidationException ex = new ValidationException(Arrays.asList("Error 1", "Error 2"));

        final ResponseEntity<List<String>> response = exceptionHandler.handleValidationException(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsExactly("Error 1", "Error 2");
    }

    @Test
    void handleDefaultException() {
        final Exception ex = new Exception("Test exception");

        final ResponseEntity<String> response = exceptionHandler.handleDefaultException(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("System is currently unavailable");
    }
}
