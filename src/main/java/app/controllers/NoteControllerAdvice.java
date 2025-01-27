package app.controllers;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoteControllerAdvice {

    @ExceptionHandler({IncorrectBodyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIncorrectBody(IncorrectBodyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler({NoDataException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoData(NoDataException ex) {
        return ex.getMessage();
    }
}
