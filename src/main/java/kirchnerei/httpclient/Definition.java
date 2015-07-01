/*
 * Copyright (c) 2015 BlueSkyFish
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package kirchnerei.httpclient;

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
	 *
	 * The format is <code>yyyy-MM-dd</code>
	 */
	public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final int STATUS_CODE_OKAY = 200;

	public static final int STATUS_CODE_BAD_REQUEST = 400;

	public static final int STATUS_CODE_UNAUTHORIZED = 401;

	public static final int STATUS_CODE_INTERNAL_SERVER = 500;

	public static final String DEFAULT_USER_AGENT = "SimpleHttpClient/10 (https://github.com/mulder3/simple-http-client)";

	public static final String HEADER_CONTENT_TYPE = "Content-Type";

	public static final String HEADER_USER_AGENT = "User-Agent";

	public static final String PATH_SEPARATOR = "/";
}
