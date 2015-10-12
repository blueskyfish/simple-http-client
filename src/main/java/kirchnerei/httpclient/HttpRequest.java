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

/**
 * An entity for a http request.
 */
public final class HttpRequest {

	private final String url;

	private final Method method;

	private final String sendData;

    /**
     * Factory method to create a GET http request.
     *
     * @param url the url
     * @return the http request
     */
    public static HttpRequest buildGET(String url) {
        return new HttpRequest(url, Method.GET, null);
    }

    /**
     * Factory method to create a POST http request.
     *
     * @param url the url
     * @param sendData the sending data
     * @return the http request
     */
    public static HttpRequest buildPOST(String url, String sendData) {
        return new HttpRequest(url, Method.POST, sendData);
    }

    /**
     * Factory method to create a PUT http request
     *
     * @param url the url
     * @param sendData the sending data
     * @return the http request
     */
    public static HttpRequest buildPUT(String url, String sendData) {
        return new HttpRequest(url, Method.PUT, sendData);
    }

    /**
     * Factory method to create a DELETE http request.
     *
     * @param url the url
     * @return the http request
     */
    public static HttpRequest buildDELETE(String url) {
        return new HttpRequest(url, Method.DELETE, null);
    }

    /**
     * Construct creates the http request.
     *
     * @param url the url
     * @param method the http method. The parameter can not be null.
     * @param sendData send data.
     */
	public HttpRequest(String url, Method method, String sendData) {
		if (method == null) {
            throw new IllegalArgumentException("parameter 'method' is null");
        }
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
