package org.example.tests;

import org.example.base.AwesomePacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    private static final byte FOOTER_1 = 0;
    private static final byte FOOTER_2 = -112;

    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;


    public static TestServer create(Integer port) throws IOException {
        TestServer server = new TestServer();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
        return server;
    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }

    private byte[] collectResponse(InputStream inputStream) throws IOException {
        int b;
        int counter = 0;
        byte[] buffer = new byte[10];
        while ((b = inputStream.read()) > -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && buffer[counter - 2] == FOOTER_1 && buffer[counter - 1] == FOOTER_2) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }

    public void start() throws IOException {
        clientSocket = serverSocket.accept();

        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedInputStream inputStream = new BufferedInputStream(clientSocket.getInputStream());

        byte[] data = collectResponse(inputStream);
        AwesomePacket packet = AwesomePacket.parse(data);
        System.out.println("Got message");

        Integer value1 = packet.getValue(1, Integer.class);
        Boolean value2 = packet.getValue(2, Boolean.class);
        String value3 = packet.getValue(3, String.class);

        System.out.println("FieldId: " + 1 + "; Value: " + value1);
        System.out.println("FieldId: " + 2 + "; Value: " + value2);
        System.out.println("FieldId: " + 3 + "; Value: " + value3);

        AwesomePacket response = AwesomePacket.create(1);
        response.setValue(1, "Thank you!");
        outputStream.write(response.toByteArray());
        outputStream.flush();
//        outputStream.close();
    }
}
