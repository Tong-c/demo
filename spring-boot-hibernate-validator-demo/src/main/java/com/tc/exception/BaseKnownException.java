package com.tc.exception;


import com.tc.common.ErrorConstant;

import java.text.MessageFormat;

public class BaseKnownException extends BaseException {

    public BaseKnownException() {

    }

    public BaseKnownException(
            int errorCode,
            String errorMessage,
            Throwable cause,
            Object data,
            int httpCode,
            String customInfo) {
        super(errorCode, errorMessage, cause, data, httpCode, customInfo);
    }

    public BaseKnownException(
            int errorCode,
            String errorMessage,
            Throwable cause,
            Object data,
            String customInfo) {
        super(errorCode, errorMessage, cause, data, customInfo);
    }

    public BaseKnownException(
            int errorCode,
            String errorMessage,
            Throwable cause,
            Object data,
            int httpCode,
            int internalErrorCode,
            String internalErrorMessage,
            String customInfo) {
        super(errorCode, errorMessage, cause, data, httpCode, internalErrorCode, internalErrorMessage, customInfo);
    }

    public BaseKnownException(int errorCode, String errorMessage) {

        this(errorCode,
                errorMessage,
                null,
                null,
                ErrorConstant.HTTPCODE_INTERNAL_SERVER_ERROR,
                errorCode,
                errorMessage,
                null);

    }

    /**
     * 提供可以携带customInfo的异常
     *
     * @param errorCode
     * @param errorMessage
     * @param customInfo   这个会在最后的log里打印出来，便于排查问题
     */
    public BaseKnownException(int errorCode, String errorMessage, String customInfo) {

        this(errorCode,
                errorMessage,
                null,
                null,
                ErrorConstant.HTTPCODE_INTERNAL_SERVER_ERROR,
                errorCode,
                errorMessage,
                customInfo);

    }

    /**
     * 返回格式化字符串的Exception
     *
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public static BaseKnownException getFormattedException(int errorCode, String errorMessage, Object... arguments) {
        return new BaseKnownException(errorCode,
                MessageFormat.format(errorMessage, arguments));
    }

    public static BaseKnownException getUserErrorException(int errorCode, String errorMessage) {

        return new BaseKnownException(errorCode,
                errorMessage,
                null,
                null,
                ErrorConstant.HTTPCODE_INTERNAL_SERVER_ERROR,
                errorCode,
                errorMessage,
                null);

    }

}
