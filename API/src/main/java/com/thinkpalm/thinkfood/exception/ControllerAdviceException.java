package com.thinkpalm.thinkfood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(ValueNullException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorResponse> handleValueNullException(ValueNullException e) {
        HttpStatus status=HttpStatus.OK;
        return new ResponseEntity<>(new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.OK;
            }

            @Override
            public ProblemDetail getBody() {
                ProblemDetail problemDetail = null;
                problemDetail.setStatus(HttpStatus.OK);
                problemDetail.setTitle("Null Value is Filled");
                problemDetail.setDetail(e.getMessage());
               return problemDetail;

            }
        },
                status);
    }

    @ExceptionHandler(DetailsNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleDetailsNotFoundException(DetailsNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
    }

    @ExceptionHandler(DetailsMissingException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleDetailsMissingException(DetailsMissingException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
    }
}



