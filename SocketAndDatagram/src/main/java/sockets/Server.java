package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int port = 4444;

    public static void main(String[] args) {
        try (ServerSocket listener = new ServerSocket(port)) {

            System.out.println("[SERVER] Waiting for the first client ...");
            Socket firstClient = listener.accept();
            System.out.println("[SERVER] First client has been connected!\n" +
                    "[SERVER] Waiting for the second client ...");
            Socket secondClient = listener.accept();
            System.out.println("[SERVER] Second client has been connected!");

            System.out.println("[SERVER] Waiting for the message from 1st client ...");
            byte b = (byte) firstClient.getInputStream().read();
            System.out.println("[SERVER] Message from 1st client was received");

            System.out.println("[SERER] Sending message to 2nd client ...");
            secondClient.getOutputStream().write(b);
            secondClient.getOutputStream().flush();

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
