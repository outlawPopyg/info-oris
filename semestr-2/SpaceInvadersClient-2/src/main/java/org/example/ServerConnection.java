package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Socket server;
    private final BufferedReader in;

    public ServerConnection(Socket socket) throws IOException {
        this.server = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String serverResponse = in.readLine();

                if (serverResponse == null) break;

                System.out.println("Server says: " + serverResponse);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
