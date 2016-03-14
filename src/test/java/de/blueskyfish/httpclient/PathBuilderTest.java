/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

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
