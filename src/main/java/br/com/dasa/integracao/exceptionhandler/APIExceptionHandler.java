package br.com.dasa.integracao.exceptionhandler;

import static java.util.stream.Collectors.toList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.dasa.integracao.exceptionhandler.ErrorResponse.ApiError;
import br.com.dasa.integracao.services.exception.BusinessException;

@Order(HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class APIExceptionHandler {

    private APIError apiError;

    @Autowired
    public APIExceptionHandler(APIError apiError) {
        this.apiError = apiError;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception, Locale locale) {
        Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();

        List<ApiError> apiErros = errors.map(ObjectError::getDefaultMessage)
                .map(code -> apiError.toApiError(code, locale, code))
                .collect(toList());

        ErrorResponse errorResponse = ErrorResponse.of(BAD_REQUEST, apiErros);
        return  ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale) {
        ErrorResponse errorResponse = ErrorResponse.of(BAD_REQUEST, apiError.toApiError("generico-formato-invalido", locale, exception.getValue()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleNegocioException(BusinessException exception, Locale locale) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), apiError.toApiError(exception.getCode(), locale, ExceptionUtils.getRootCauseMessage(exception)));
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
