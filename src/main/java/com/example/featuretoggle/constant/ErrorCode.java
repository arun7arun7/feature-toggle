package com.example.featuretoggle.constant;

public enum ErrorCode {

    UNKNOWN_STRATEGY_FOR_FEATURE_EVALUATION("Unknown strategy"),
    UNKNOWN_OPERATOR_FOR_FEATURE_EVALUATION("Unknown operator"),
    FEATURE_NOT_FOUND("feature is not found"),
    FEATURE_KEY_ALREADY_EXISTS("Duplicate feature key in this environment"),
    FEATURE_NAME_ALREADY_EXISTS("Duplicate feature name in this environment"),
    ENVIRONMENT_KEY_ALREADY_EXISTS("DUPLICATE KEY FOUND"),
    ENVIRONMENT_NAME_ALREADY_EXISTS("DUPLICATE NAME FOUND"),
    QUERY_EXECUTION_ERROR("Query Execution Error"),
    NO_ENTRY_FOUND("No Entry Found");
    private String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
