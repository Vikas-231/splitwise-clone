package com.splitwise.clone.model.exception;

public enum ResponseErrorCode {
    STATIC_VALIDATION_FAILED("400.0000001","Static validation failed"),
    USER_NOT_FOUND("404.0000001","User not found"),
    INVALID_REQUEST("400.0000003","Invalid request"),
    INVALID_REFRESH_TOKEN("401.0000001","Invalid refresh token"),
    DATA_VALIDATION_FAILED("400.0000002","Data validation failed"),
    USER_NOT_AUTHENTICATED("401.0000002","User not authenticated"),
    ACCESS_DENIED("403.0000001","Access denied"),
    BAD_REQUEST("400.0000004","Bad request"),
    INTERNAL_SERVER_ERROR("500.0000001","Internal server error");

    private final String code;

    private final String title;

    private ResponseErrorCode(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }
    public String getTitle() {
        return title;
    }

    private static  ResponseErrorCode fromCode(String code) {
        for (ResponseErrorCode errorCode : ResponseErrorCode.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return null;
    }
}
