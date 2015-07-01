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

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log {

	private static final Logger LOGGER = Logger.getLogger("kirchnerei.httpclient");

	public static void debug(String message, Object... args) {
		LOGGER.finer(format(message, args));
	}

	public static void warn(Throwable t, String message, Object... args) {
		LOGGER.log(Level.WARNING, format(message, args), t);
	}

	static String format(String message, Object... args) {
		if (args != null && args.length > 0) {
			return String.format(message, args);
		}
		return message;
	}

	private Log() {}
}
