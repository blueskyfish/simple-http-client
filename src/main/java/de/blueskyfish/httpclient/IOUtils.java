/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class IOUtils {

    public static void close(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFrom(InputStream input, String encoding, int bufferSize) throws IOException {
        StringBuilder sb = new StringBuilder(bufferSize);
        char[] buffer = new char[bufferSize];
        int count;
        Reader reader = new InputStreamReader(input, encoding);
        while ((count = reader.read(buffer)) != -1) {
            sb.append(buffer, 0, count);
        }
        reader.close();
        return sb.toString();
    }
}
