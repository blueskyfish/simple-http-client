/*
 * Copyright (c) 2015 <Kirchnerei>
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

/**
 * An entity for a http request.
 */
public class HttpRequest {

	private final String url;

	private final Method method;

	private final String sendData;

	public HttpRequest(String url, Method method, String sendData) {
		this.url = url;
		this.method = method;
		this.sendData = sendData;
		if (!method.allowSend() && sendData != null && !sendData.isEmpty()) {
			throw new IllegalArgumentException("send data is not allow in method '" + method + "'");
		}
	}

	public String getUrl() {
		return url;
	}

	public Method getMethod() {
		return method;
	}

	public String getSendData() {
		return sendData;
	}

	public boolean hasSendData() {
		return method.allowSend() && sendData != null && !sendData.isEmpty();
	}

	@Override
	public String toString() {
		return "HttpRequest {" + " " + method + ": [" + url + "] " + '}';
	}
}
