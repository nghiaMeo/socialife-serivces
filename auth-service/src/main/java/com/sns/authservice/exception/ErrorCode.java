package com.sns.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    USERNAME_ALREADY_EXISTS(409, "Username Already Exists", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
