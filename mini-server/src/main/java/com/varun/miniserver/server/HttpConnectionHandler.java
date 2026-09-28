package com.varun.miniserver.server;

import com.varun.miniserver.http.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class HttpConnectionHandler implements Runnable {
    private final Socket socket;
    private final Router router;

    public HttpConnectionHandler(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    public void run() {
        try (Socket client = socket;
             InputStream in = client.getInputStream();
             OutputStream out = client.getOutputStream()
        ) {
            HttpRequest req = HttpParser.parse(in);
            System.out.println(req.getMethod() + " " + req.getPath());
            HttpResponse res = router.route(req);
            out.write(res.toHttpString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
