package com.varun.miniserver.http;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private final int statusCode;
    private final String statusMessage, body;

    public HttpResponse(int c, String m, String b) {
        statusCode = c;
        statusMessage = m;
        body = b;
    }

    public String toHttpString() {
        int len = body.getBytes(StandardCharsets.UTF_8).length;
        return "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\nContent-Type: application/json\r\nContent-Length: " + len + "\r\nConnection: close\r\n\r\n" + body;
    }
}
