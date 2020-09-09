package com.example.featuretoggle.exception;

import com.example.featuretoggle.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.example.featuretoggle"})
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public @ResponseBody
    ApiResponse handleException(ApiException ex) {
        ApiResponse apiOutput = new ApiResponse(ex.getMessage());
        return apiOutput;
    }



}
