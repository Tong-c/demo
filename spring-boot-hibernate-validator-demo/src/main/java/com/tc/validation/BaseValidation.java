package com.tc.validation;


import com.tc.common.ErrorConstant;
import com.tc.exception.BaseKnownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

public class BaseValidation {
    private static Logger log = LoggerFactory.getLogger(BaseValidation.class);

    public static void throwBaseKnownExceptionIfEmpty(Object obj, String param) {
        String errorMessage = MessageFormat.format(ErrorConstant.ERRORMESSAGE_INVALID_PARAMS, param, param + " 不能为空");
        throwBaseKnownExceptionIfEmpty(obj, ErrorConstant.ERRORCODE_INVALID_PARAMS, errorMessage);
    }

    public static void throwBaseKnownExceptionIfEmpty(Object obj, Integer errorCode, String errorMessage) {
        if (isEmpty(obj)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownExceptionIfNotEmpty(Object obj, Integer errorCode, String errorMessage) {
        if (!isEmpty(obj)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownExceptionIfEquals(Object obj, Object obj2, Integer errorCode, String errorMessage) {
        if (!isEmpty(obj) && !isEmpty(obj2) && obj.equals(obj2)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownExceptionIfNotEquals(Object obj, Object obj2, Integer errorCode, String errorMessage) {
        if (!obj.equals(obj2)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownException(String errorMessage) {
        throwBaseKnownException(ErrorConstant.ERRORCODE_INVALID_PARAMS, errorMessage);
    }

    public static void throwBaseKnownException(Integer errorCode, String errorMessage) {
        log.warn(errorMessage);
        throw new BaseKnownException(errorCode, errorMessage);
    }

    public static void throwBaseUnKnownExceptionIfEmpty(Object obj, String param) {
        String errorMessage = MessageFormat.format(ErrorConstant.ERRORMESSAGE_INVALID_PARAMS, param, param + " 不能为空");
        throwBaseUnKnownExceptionIfEmpty(obj, ErrorConstant.ERRORCODE_INVALID_PARAMS, errorMessage);
    }

    public static void throwBaseUnKnownExceptionIfEmpty(Object obj, Integer errorCode, String errorMessage) {
        if (isEmpty(obj)) {
            throwBaseUnKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseUnKnownException(Integer errorCode, String errorMessage) {
        log.warn(errorMessage);
        throw new BaseKnownException(errorCode, errorMessage);
    }

    public static boolean isEmpty(Object obj) {
        if ((obj instanceof Collection && CollectionUtils.isEmpty((Collection<?>) obj)) /*|| ZZStringUtils.isEmpty(obj)*/
                || obj instanceof Map/* && MapUtils.isEmpty((Map<?, ?>) obj)*/) {
            return true;
        }
        return false;
    }

    public static <T> boolean isContains(Collection<T> collection, T value) {
        if (!BaseValidation.isEmpty(collection) && collection.contains(value)) {
            return true;
        }
        return false;
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throwBaseKnownException(String.valueOf(errorMessage));
        }
    }

}
