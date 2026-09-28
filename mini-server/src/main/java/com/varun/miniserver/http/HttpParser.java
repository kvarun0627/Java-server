package com.varun.miniserver.http;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    public static HttpRequest parse(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        String line = reader.readLine();
        if (line == null || line.isBlank()) throw new IOException("Invalid HTTP request");
        String[] p = line.split(" ");
        if (p.length != 3) throw new IOException("Invalid request line");
        HttpRequest r = new HttpRequest();
        r.setMethod(p[0]);
        r.setPath(p[1]);
        r.setVersion(p[2]);
        int contentLength = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colon = line.indexOf(':');
            if (colon < 0) continue;
            String k = line.substring(0, colon).trim(), v = line.substring(colon + 1).trim();
            r.getHeaders().put(k, v);
            if (k.equalsIgnoreCase("Content-Length")) contentLength = Integer.parseInt(v);
        }
        if (contentLength > 0) {
            char[] body = new char[contentLength];
            int total = 0, n;
            while (total < contentLength && (n = reader.read(body, total, contentLength - total)) != -1) total += n;
            r.setBody(new String(body, 0, total));
        } else r.setBody("");
        return r;
    }
}
