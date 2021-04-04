package br.com.dasa.integracao.exceptionhandler;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GeneralExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    private APIError apiError;

    @Autowired
    public GeneralExceptionHandler(APIError apiError) {
        this.apiError = apiError;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
        LOG.error("Erro não esperado", exception);
        ErrorResponse errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR, apiError.toApiError("erro-generico", locale, ExceptionUtils.getRootCauseMessage(exception)));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
