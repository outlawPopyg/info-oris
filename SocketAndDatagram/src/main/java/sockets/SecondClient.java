package sockets;

import java.io.IOException;
import java.net.Socket;

public class SecondClient {
    private static final int port = 4444;
    private static final String host = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(host, port)) {

            byte b = (byte) socket.getInputStream().read();
            System.out.println("Received " + b);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
