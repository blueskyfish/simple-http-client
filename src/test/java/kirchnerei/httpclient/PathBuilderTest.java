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

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PathBuilderTest {

	@Test
	public void testPathWithDate() throws Exception {
		Date date = Definition.DEFAULT_DATE_FORMAT.parse("2015-03-28");
		String url = PathBuilder.toUrl("orders", date, "refresh", 3, "days");

		assertEquals("/orders/2015-03-28/refresh/3/days", url);
	}

	@Test
	public void testPathWithGermanUmlaute() {
		String url = PathBuilder.toUrl("Großgündheim", "mit", 3, "Bälle");
		assertEquals("/Gro%C3%9Fg%C3%BCndheim/mit/3/B%C3%A4lle", url);
	}

	@Test
	public void testPathWhitespaces() {
		String url = PathBuilder.toUrl("Susanne Mayer", "address", "Main Street");
		assertEquals("/Susanne+Mayer/address/Main+Street", url);
	}
}
