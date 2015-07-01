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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import static kirchnerei.httpclient.Definition.*;

/**
 * The http client manages the request and responses to the external web service / rest service.
 *
 * There are some restriction:
 *
 * <ul>
 *     <li>The client has no session management.</li>
 *     <li>if a {@link HttpClientStore} is present, then the client use a header token for the communication with the service</li>
 * </ul>
 */
public class HttpClient {

	private final HttpConfiguration configuration;

	private final HttpClientStore clientStore;

	/**
	 * Contains the headers for the request.
	 */
	private final Map<String, String> headers = new LinkedHashMap<String, String>();

	public HttpClient(HttpConfiguration configuration, HttpClientStore clientStore) {
		if (configuration == null) {
			throw new IllegalArgumentException("http client needs a configuration. parameter is null");
		}
		this.configuration = configuration;
		this.clientStore = clientStore;
	}

	/**
	 * Starts a request to the server with the given url and returns the response.
	 *
	 * @param httpRequest contains the url, request method and optional the send data.
	 * @return the result
	 * @throws IllegalArgumentException when the parameter is null.
	 */
	public HttpResponse execute(HttpRequest httpRequest) {
		if (httpRequest == null) {
			throw new IllegalArgumentException("http client is expected a action parameter, not null");
		}
		long dtStart = System.currentTimeMillis();
		HttpURLConnection conn = null;
		InputStream input = null;
		try {
			conn = createRequest(httpRequest);
			updateHeaderFromConnection(conn);
			int statusCode = conn.getResponseCode();
			String content = null;
			if (statusCode == STATUS_CODE_OKAY) {
				input = conn.getInputStream();
			} else if (statusCode >= STATUS_CODE_INTERNAL_SERVER) {
				content = "{\"title\": \"Internal Server Error\", \"message\": \"Unexpected error from the server.\"}";
			} else {
				input = conn.getErrorStream();
			}
			if (input != null) {
				content = IOUtils.readFrom(input, getInputEncoding(), getBufferSize());
			}
			Log.debug("[%s] %s", statusCode, content);
			return new HttpResponse(statusCode, content);
		} catch (IOException e) {
			throw new HttpClientException(e, "can not read the result content");
		} finally {
			IOUtils.close(input);
			closeConnection(dtStart, httpRequest.getUrl(), conn);
		}
	}



	public boolean hasClientStore() {
		return clientStore != null;
	}

	/**
	 * Returns the header token.
	 *
	 * @return null or the header token
	 */
	public String getHeaderToken() {
		return hasClientStore() ? clientStore.getToken() : null;
	}

	/**
	 * Store the header token from an execute request (it is from the response header!)
	 */
	synchronized void storeHeaderToken(String token) {
		if (hasClientStore()) {
			clientStore.putToken(token);
			Log.debug("store the header token [%s]", token);
		}
	}


	String buildUrl(String url) {
		String baseUrl = configuration.getBaseUrl();
		if (StringUtils.isEmpty(baseUrl)) {
			throw new HttpClientException("missing the base url");
		}
		return baseUrl + url;
	}

	HttpURLConnection createRequest(HttpRequest httpRequest) {
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) new URL(buildUrl(httpRequest.getUrl())).openConnection();
		} catch (IOException e) {
			throw new HttpClientException(e, "malformed request \"%s\"", httpRequest.getUrl());
		}
		try {
			conn.setRequestMethod(httpRequest.getMethod().toString());
		} catch (ProtocolException e) {
			throw new HttpClientException(e, "method \"%s\" is not allow ", httpRequest.getMethod());
		}
		setHeadersToConnection(conn);
		setOutputToConnection(httpRequest, conn);
		return conn;
	}

	/**
	 * Update the header from the response (only the ebbemeister token is important).
	 *
	 * @param conn the connection with response.
	 */
	void updateHeaderFromConnection(HttpURLConnection conn) {
		String headerTokenName = configuration.getHeaderTokenName();
		if (!hasClientStore() || (headerTokenName == null || headerTokenName.isEmpty())) {
			// the header token is not store
			// two reasons:
			// -- no client store is present
			// -- no header name is exist
			return;
		}

		int index = (conn.getHeaderFieldKey(0) == null) ? 1 : 0;
		while (true) {
			String key = conn.getHeaderFieldKey(index);
			if (key == null) {
				break;
			}
			if (headerTokenName.equalsIgnoreCase(key)) {
				String value = conn.getHeaderField(index);
				storeHeaderToken(value);
				break;
			}
			++index;
		}
	}

	void setHeadersToConnection(HttpURLConnection conn) {

		if (!headers.containsKey(HEADER_CONTENT_TYPE)) {
			headers.put(HEADER_CONTENT_TYPE, getContentType());
		}
		if (!headers.containsKey(HEADER_USER_AGENT)) {
			headers.put(HEADER_USER_AGENT, getUserAgent());
		}

		// set always the header token
		String headerTokenName = configuration.getHeaderTokenName();
		if (hasClientStore() && headerTokenName != null && !headerTokenName.isEmpty()) {
			String token = getHeaderToken();
			headers.put(headerTokenName, token);
		}

		// put into the http connection
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			conn.setRequestProperty(entry.getKey(), entry.getValue());
		}
	}

	void setOutputToConnection(HttpRequest httpRequest, HttpURLConnection conn) {
		if (httpRequest.hasSendData()) {
			conn.setDoOutput(true);
			OutputStream output = null;
			try {
				output = conn.getOutputStream();
				Writer writer = new OutputStreamWriter(output, getOutputEncoding());
				writer.append(httpRequest.getSendData()).flush();
			} catch (IOException e) {
				throw new HttpClientException(e, "output can not written <%s: %s>",
						httpRequest.getMethod(), httpRequest.getUrl()
				);
			} finally {
				IOUtils.close(output);
			}
		}
	}

	String getContentType() {
		return StringUtils.prepare(configuration.getContentType(), DEFAULT_CONTENT_TYPE);
	}

	String getUserAgent() {
		return StringUtils.prepare(configuration.getUserAgent(), DEFAULT_USER_AGENT);
	}

	String getOutputEncoding() {
		return StringUtils.prepare(configuration.getOutputEncoding(), DEFAULT_OUTPUT_ENCODING);
	}

	String getInputEncoding() {
		return  StringUtils.prepare(configuration.getInputEncoding(), DEFAULT_INPUT_ENCODING);
	}

	int getBufferSize() {
		int bufferSize = prepare(configuration.getBufferSize(), DEFAULT_BUFFER_SIZE);

		if (bufferSize < MIN_BUFFER_SIZE) {
			bufferSize = MIN_BUFFER_SIZE;
		}
		if (bufferSize > MAX_BUFFER_SIZE) {
			bufferSize =MAX_BUFFER_SIZE;
		}
		return bufferSize;
	}

	static void closeConnection(long dtStart, String url, HttpURLConnection conn) {
		if (conn != null) {
			conn.disconnect();
		}
		Log.debug("connection from '%s' is closed [%s ms]", url, (System.currentTimeMillis() - dtStart));
	}


	static int prepare(int value, int def) {
		if (value <= 0) {
			return def;
		}
		return value;
	}

}
