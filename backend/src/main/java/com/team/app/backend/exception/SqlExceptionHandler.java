package com.team.app.backend.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class SqlExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity handleDataAccessException(DataAccessException ex) {
        log.error(ex.toString());
        return  ResponseEntity.badRequest().body(messageSource
                .getMessage("data.base.error", null, LocaleContextHolder.getLocale()));
    }

}
