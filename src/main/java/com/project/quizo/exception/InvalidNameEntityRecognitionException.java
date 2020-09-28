package com.project.quizo.exception;

import org.springframework.http.HttpStatus;

public class InvalidNameEntityRecognitionException extends CustomException {
    private static final String NER_IS_INVALID = "Named entity recognition is invalid : ";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidNameEntityRecognitionException(String object) {
        super(NER_IS_INVALID + object, HTTP_STATUS);
    }
}
