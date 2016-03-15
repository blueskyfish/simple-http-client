/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Contains some constants
 */
public class Definition {

    public static final int MIN_BUFFER_SIZE = 4 * 1024;  // 4 kb

    public static final int MAX_BUFFER_SIZE = 512 * 1024; // 512 kb or 0.5 mb

    public static final int DEFAULT_BUFFER_SIZE = MIN_BUFFER_SIZE;

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    public static final String DEFAULT_INPUT_ENCODING = DEFAULT_ENCODING;

    public static final String DEFAULT_OUTPUT_ENCODING = DEFAULT_ENCODING;

    /**
     * The date formatter that parse string to date or format date to string in the ISO 8601.
     * <a href="http://en.wikipedia.org/wiki/ISO_8601"></a>.
     * <p>
     * The format is <code>yyyy-MM-dd</code>
     */
    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final int STATUS_CODE_OKAY = 200;

    public static final int STATUS_CODE_BAD_REQUEST = 400;

    public static final int STATUS_CODE_UNAUTHORIZED = 401;

    public static final int STATUS_CODE_INTERNAL_SERVER = 500;

    public static final String DEFAULT_USER_AGENT = "SimpleHttpClient/10 (https://github.com/blueskyfish/simple-http-client)";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    public static final String HEADER_ACCEPT = "Accept";

    public static final String HEADER_USER_AGENT = "User-Agent";

    public static final String PATH_SEPARATOR = "/";

    private Definition() {
    }
}
