package org.example;

import org.example.base.AwesomePacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AwesomeServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Integer port;

    private AwesomeServer() {

    }

    public static AwesomeServer create(Integer port) throws IOException {
        AwesomeServer server = new AwesomeServer();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
        return server;
    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] extendArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, extendArray, 0, oldSize);
        return extendArray;
    }

    private byte[] readInput(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((b = stream.read()) != -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && AwesomePacket.compareEOP(buffer, counter - 1)) {
                break;
            }
         }

        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);

        return data;
    }

    public void start() throws IOException {
        clientSocket = serverSocket.accept();

        InputStream reader = clientSocket.getInputStream();
        OutputStream writer = clientSocket.getOutputStream();

        byte[] data = readInput(reader);
        AwesomePacket packet = AwesomePacket.parse(data);

        Integer value1 = packet.getValue(1, Integer.class);
        Boolean value2 = packet.getValue(2, Boolean.class);
        String value3 = packet.getValue(3, String.class);

        System.out.println("Field ID : " + 1 + "; Value : " + value1);
        System.out.println("Field ID : " + 2 + "; Value : " + value2);
        System.out.println("Field ID : " + 3 + "; Value : " + value3);

        AwesomePacket response = AwesomePacket.create(1);
        response.setValue(1, "Thanks for message");

        writer.write(response.toByteArray());
        writer.flush();
    }
}
