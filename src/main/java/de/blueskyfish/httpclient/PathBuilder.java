/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

import java.text.DateFormat;
import java.util.Date;

/**
 * A helper class to build urls.
 */
public final class PathBuilder {

    public static String toUrl(Object... segments) {

        StringBuilder sb = new StringBuilder();
        for (Object segment : segments) {
            if (segment == null) {
                continue;
            }
            if (segment instanceof Date) {
                DateFormat dateFormat = Definition.DEFAULT_DATE_FORMAT;
                sb.append(Definition.PATH_SEPARATOR).append(dateFormat.format((Date) segment));
            } else if (segment instanceof String) {
                sb.append(Definition.PATH_SEPARATOR).append(StringUtils.urlEncode((String) segment, Definition.DEFAULT_ENCODING));
            } else {
                sb.append(Definition.PATH_SEPARATOR).append(segment);
            }
        }
        return sb.toString();
    }
}
