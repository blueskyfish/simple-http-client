/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

import java.io.IOException;
import java.io.InputStream;

public class ErrorBuilder {

    private static final String error;

    static {
        InputStream input = ErrorBuilder.class.getResourceAsStream("error.json");
        String s;
        try {
            s = IOUtils.readFrom(input, Definition.DEFAULT_ENCODING, 512);
        } catch (IOException e) {
            e.printStackTrace();
            s = "%s";
        } finally {
            IOUtils.close(input);
        }
        error = s;
    }

    public static String withMessage(String message) {
        return String.format(error, message);
    }

    public static String withMessage(String format, Object... args) {
        return withMessage(String.format(format, args));
    }
}
