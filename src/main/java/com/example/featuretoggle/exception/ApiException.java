package com.example.featuretoggle.exception;

import com.example.featuretoggle.constant.ErrorCode;
import lombok.Data;

@Data
public class ApiException extends RuntimeException {

    private String code, message;

    public ApiException(ErrorCode code) {
        super(code.getErrorMessage());
        this.code = code.name();
        this.message = code.getErrorMessage();
    }


    public ApiException(ErrorCode code, String message) {
        super(message);
        this.code = code.name();
        this.message = message;
    }

}

