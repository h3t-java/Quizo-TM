package com.project.quizo.Exception;

import org.springframework.http.HttpStatus;

public class TokenIsInvalid extends CustomException {
    private static final String TOKEN_IS_INVALID = "Verification token is invalid : ";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public TokenIsInvalid(String token) {
        super(TOKEN_IS_INVALID + token, HTTP_STATUS);
    }
}
