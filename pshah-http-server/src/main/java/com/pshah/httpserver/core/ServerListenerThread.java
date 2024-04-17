package com.pshah.httpserver.core;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        while (serverSocket.isBound() && !serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();

                LOGGER.info("Connection accepted" + socket.getInetAddress());
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                String html = "<html><head><title>Simple java Http server</title></head><body><h1>Pratiksha's simple http server</h1></body></html>";

                final String CRLF = "\n\r";

                String response =
                        "HTTP/1.1 200 OK" + CRLF + //Status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                "Content-Length: " + html.getBytes().length + CRLF + //header
                                CRLF + html + CRLF + CRLF;

                outputStream.write(response.getBytes());

                inputStream.close();
                outputStream.close();
                socket.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
