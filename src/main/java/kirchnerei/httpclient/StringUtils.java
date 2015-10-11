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
	 * @param def the default string, if the value is empty
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
	 * @param urlPart the url string
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

	private StringUtils() { }
}
