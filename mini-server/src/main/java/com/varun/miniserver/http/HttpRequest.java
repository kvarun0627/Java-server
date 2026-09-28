package com.varun.miniserver.http;

import java.util.*;

public class HttpRequest {
    private String method, path, version, body;
    private final Map<String, String> headers = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String v) {
        method = v;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String v) {
        path = v;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String v) {
        version = v;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String v) {
        body = v;
    }
}
