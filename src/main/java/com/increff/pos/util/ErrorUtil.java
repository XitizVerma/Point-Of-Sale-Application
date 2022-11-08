package com.increff.pos.util;

import com.increff.pos.exception.ApiException;

import java.util.List;

public class ErrorUtil {

    public static void throwError(List<String> errorList) throws ApiException {
        String errorStr = "";
        for (String s : errorList) {
            errorStr += s + "\n";
        }
        throw new ApiException(errorStr);
    }
}
