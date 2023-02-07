package com.stackerrors.util;

import java.util.UUID;

public class StringUtil {

    public static String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

}
