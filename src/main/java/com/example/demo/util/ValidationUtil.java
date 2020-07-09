package com.example.demo.util;

import com.example.demo.exception.NotFoundException;

public class ValidationUtil {

    public static void isNotFound(boolean expression) {
        if (expression) {
            throw new NotFoundException();
        }
    }
}
