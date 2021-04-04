package br.com.dasa.integracao.exceptionhandler;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.dasa.integracao.exceptionhandler.ErrorResponse.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class APIError {

    private static final String NO_MESSAGE_AVAILABLE = "Nenhuma mensagem disponivel";

    private final MessageSource apiErrorMessageSource;

    public ApiError toApiError(String code, Locale locale, Object... args) {
        String message;

        try {
            message = apiErrorMessageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            log.error("Não foi possível encontrar nenhuma mensagem para {} e locale {}", code, locale);
            message = NO_MESSAGE_AVAILABLE;
        }

        return new ApiError(code, message, args);
    }

}
