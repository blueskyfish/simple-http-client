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
        return toUrl(null, segments);
   }

    public static String toUrl(String baseUrl, Object... segments) {
        String tempUrl = encodeBaseUrl(baseUrl);
        StringBuilder sb = StringUtils.isEmpty(tempUrl) ? new StringBuilder() : new StringBuilder(tempUrl);
        for (Object segment : segments) {
            if (segment == null) {
                continue;
            }
            if (segment instanceof Date) {
                DateFormat dateFormat = Definition.DEFAULT_DATE_FORMAT;
                sb.append(Definition.PATH_SEPARATOR).append(dateFormat.format((Date) segment));
            } else if (segment instanceof String) {
                sb.append(Definition.PATH_SEPARATOR).append(
                        StringUtils.urlEncode((String) segment, Definition.DEFAULT_ENCODING)
                );
            } else {
                sb.append(Definition.PATH_SEPARATOR).append(segment);
            }
        }
        return sb.toString();
    }

    static String encodeBaseUrl(String baseUrl) {
        if (StringUtils.isEmpty(baseUrl)) {
            return null;
        }
        String tempUrl = StringUtils.urlEncode(baseUrl, Definition.DEFAULT_ENCODING);
        if (tempUrl != null && !tempUrl.startsWith(Definition.PATH_SEPARATOR)) {
            return Definition.PATH_SEPARATOR + tempUrl;
        }
        return tempUrl;
    }
}
