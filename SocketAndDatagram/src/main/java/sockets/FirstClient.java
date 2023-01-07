package sockets;

import java.io.IOException;
import java.net.Socket;

public class FirstClient {
    private static final int port = 4444;
    private static final String host = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(host, port)) {

            socket.getOutputStream().write(0x1);
            socket.getOutputStream().flush();

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
