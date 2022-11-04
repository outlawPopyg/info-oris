package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AwesomeClient {
    private String ip;
    private int port;

    public AwesomeClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public void sendMessage(String message) throws IOException {
        Socket socket = new Socket(ip, port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(message);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        reader.lines().forEach(System.out::println);
    }
}
