package org.example.test;

import org.example.base.AwesomePacket;

import java.io.*;
import java.net.Socket;

public class TestClient {
    private static final byte FOOTER_1 = 0;
    private static final byte FOOTER_2 = -112;

    private static byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }

    private static byte[] collectResponse(InputStream inputStream) throws IOException {
        int b;
        int counter = 0;
        byte[] buffer = new byte[10];
        while ((b = inputStream.read()) != -1) {
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

    public static void sendMessage(int port, String host, AwesomePacket packet) throws IOException {
        Socket socket = new Socket(host, port);

        OutputStream outputStream = socket.getOutputStream();
        BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());

        outputStream.write(packet.toByteArray());
        outputStream.flush();
        System.out.println("Sent");
//        outputStream.close();

        byte[] data = collectResponse(inputStream);
        AwesomePacket response = AwesomePacket.parse(data);

        String value1 = response.getValue(1, String.class);
        System.out.println("FieldId: " + 1 + "; Value: " + value1);
    }
}
