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
 * The configuration for the {@link HttpClient}.
 */
public class HttpConfiguration {

	/**
	 * The buffer size for reading and writing from / to the http connection stream
	 */
	private int bufferSize;

	/**
	 * The encoding from the input stream
	 */
	private String inputEncoding;

	/**
	 * The encoding from the output stream
	 */
	private String outputEncoding;

	/**
	 * The user agent
	 */
	private String userAgent;

	/**
	 * The content type
	 */
	private  String contentType;

	/**
	 * The base url for all requests.
	 */
	private String baseUrl;

	/**
	 * The name of the header for the token. If the header name is empty or null, then the token is not stored.
	 */
	private String headerTokenName;

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getInputEncoding() {
		return inputEncoding;
	}

	public void setInputEncoding(String inputEncoding) {
		this.inputEncoding = inputEncoding;
	}

	public String getOutputEncoding() {
		return outputEncoding;
	}

	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getHeaderTokenName() {
		return headerTokenName;
	}

	public void setHeaderTokenName(String headerTokenName) {
		this.headerTokenName = headerTokenName;
	}
}
