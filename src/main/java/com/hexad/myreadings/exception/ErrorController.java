package com.hexad.myreadings.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MyLearningsException.class)
    public ResponseEntity<ErrorBean> handleNotFound(MyLearningsException exception)
    {
        logger.warn(exception.getErrorMessage());
        return new ResponseEntity<ErrorBean>(new ErrorBean(exception.getErrorCode(), exception.getErrorMessage())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalError(Exception e)
    {
        logger.error("Unhandled Exception in Controller", e);
        return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
