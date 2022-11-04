package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class AwesomeServer {

    private ServerSocket serverSocket;

    public AwesomeServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        Socket clientSocket = serverSocket.accept();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        reader.lines().forEach(System.out::println);
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        writer.println("Thanks for message");
    }
}
