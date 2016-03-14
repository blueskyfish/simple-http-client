/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Helper class for using with strings.
 */
public final class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Checks whether the value is empty, then it returns the default string, otherwise it returns the value.
     *
     * @param value the given value
     * @param def   the default string, if the value is empty
     * @return the string
     */
    public static String prepare(String value, String def) {
        if (isEmpty(value)) {
            return def;
        }
        return value;
    }

    /**
     * Encodes an url part string with the given encoding.
     *
     * @param urlPart  the url string
     * @param encoding the encoding
     * @return the encoded url string
     */
    public static String urlEncode(String urlPart, String encoding) {
        try {
            return URLEncoder.encode(urlPart, encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private StringUtils() {
    }
}
