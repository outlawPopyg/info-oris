package org.example;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;
    private final List<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;

        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = in.readLine();

                if (request.contains("left")) {
                    out.println("player has moved left");
                } else if (request.contains("right")) {
                    out.println("player has moved right");
                } else if (request.contains("shoot")) {
                    out.println("player has shot");
                } else if (request.contains("broadcast")) {
                    outToAll(request);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.close();
        }
    }

    private void outToAll(String request) {
        for (ClientHandler aClient : clients) {
            aClient.out.println(request);
        }
    }
}
