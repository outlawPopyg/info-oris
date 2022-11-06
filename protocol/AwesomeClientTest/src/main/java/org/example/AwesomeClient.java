package org.example;

import org.example.base.AwesomePacket;

import java.io.*;
import java.net.Socket;

public class AwesomeClient {

    private String host;
    private Integer port;
    private Socket socket;
    private InputStream reader;
    private OutputStream writer;

    private AwesomeClient() {

    }
    public static AwesomeClient initConnection(String host, Integer port) throws IOException {
        AwesomeClient client = new AwesomeClient();
        client.port = port;
        client.host = host;
        client.socket = new Socket(host, port);
        client.reader = client.socket.getInputStream();
        client.writer = client.socket.getOutputStream();
        return client;
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

    public void sendMessage(AwesomePacket packet) throws IOException {
        writer.write(packet.toByteArray());
        writer.flush();

        byte[] data = readInput(reader);
        AwesomePacket response = AwesomePacket.parse(data);

        String value = response.getValue(1, String.class);
        System.out.println("Сообщение: " + value);
    }

}
