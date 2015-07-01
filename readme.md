

# Simple HTTP Client

A simple HTTP client that has no dependencies on other libraries. Only Java classes are used, which are included in
the Java Runtime.

## Overview

The http client manages the request and response from the web service / rest service.

There are some advantage:

* Simple to configure
* Simple to use for REST requests
* No dependencies to other java libraries.
* Simple to use in Android.
* Simple to use in other java clients.


There are some restriction:

* The client has not a session management
* The client is not built for request of html pages


## Configuration

the http client must have some settings.

Name                      | Description
------------------------- | --------------------------------------------------
bufferSize                | The buffer size for reading and writing from / to the http connection stream
inputEncoding             | The encoding from the input stream
outputEncoding            | The encoding from the output stream
userAgent                 | The user agent
contentType               | The content type
baseUrl                   | The base url for all requests.
headerTokenName           | The name of the header for the token. If the header name is empty or null, then the token is not stored.


## Token Client Store

Normally a REST service has no session management. Sometimes there is a header token for the authentication the request.
In this case you are able to store the token in a separated store in order to get the token in the next request.

**How to configure?**

* Creates a class with the interface `HttpClientStore`

```java
public class MyHttpClientStore implements HttpClientStore {

	private String token;

	@Override
	public void putToken(String token) {
		this.token = token;
	}

	@Override
	public String getToken() {
		return token;
	}
}
```

* Create an instance and give it to the client constructor

```java
HttpConfiguration configuration =
MyHttpClientStore clientStore = new MyHttpClientStore();

HttpClient httpClient = new HttpClient(configuration, clientStore);

```


## Example

### GET

```java

HttpConfiguration configuration = ...
HttpClient httpClient = new HttpClient(configuration, null);

String url = PathBuilder.toUrl("order", date, "last", "3" "days");
// e.g: url = "/order/2015-02-12/last/3/days"

HttpRequest request = new HttpRequest(Method.GET, url, null); // no data to send
HttpResponse response = httpClient.execute(request);

if (response.getStatus() == Definition.STATUS_CODE_OKAY) {

	System.out.println(response.getContent());
}


```

### POST

```java

HttpConfiguration configuration = ...
HttpClient httpClient = new HttpClient(configuration, null);

String url = PathBuilder.toUrl("order", date, "add");
// e.g: url = "/order/2015-02-12/add"

// send a JSON to the service
String sendData = "{\"orderId\": 345, \"name\": \"Shoes\"}";

HttpRequest request = new HttpRequest(Method.POST, url, sendData);
HttpResponse response = httpClient.execute(request);

if (response.getStatus() == Definition.STATUS_CODE_OKAY) {

	String content = response.getContent();
	// e.g: "{\"status\": \"okay\"}"
	System.out.println(content);
}


```


## Next Steps

1. Create test cases
2. Improve the documentation
3. Fixed issues

## Find an issue

Please insert an issue <https://github.com/blueskyfish/simple-http-client/issues>



## License

	The MIT License

	Copyright (c) 2015 <BlueSkyFish>

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.