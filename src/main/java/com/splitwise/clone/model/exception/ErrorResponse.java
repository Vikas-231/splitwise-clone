package com.splitwise.clone.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private ResponseErrorCode errorCode;

    private String title;

    private String message;
}
