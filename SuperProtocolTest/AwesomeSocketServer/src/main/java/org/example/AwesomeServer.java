package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.base.AwesomePacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

public class AwesomeServer implements Runnable {
    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ThreadPoolExecutor serverPool;

    private InputStream inputStream;
    private OutputStream outputStream;

    private ObjectMapper mapper = new ObjectMapper();

    private AwesomeServer() {}

    public static AwesomeServer create(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        AwesomeServer server = new AwesomeServer();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;
        return server;
    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }

    private byte[] readInput(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((b = stream.read()) > -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 2 && SuperPacket.compareEndOfPacket(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }


    @Override
    public void run() {
        try {
            clientSocket = serverSocket.accept();
            outputStream = clientSocket.getOutputStream();
            inputStream = clientSocket.getInputStream();

            while (true) {

                SuperPacket handshake = SuperPacket.create(1);
                handshake.setValue(1, "ping");

                outputStream.write(handshake.toByteArray());
                outputStream.flush();

                byte[] data = readInput(inputStream);
                SuperPacket packet = SuperPacket.parse(data);
                System.out.println(packet.getValue(1, String.class));

                SuperPacket standardPacket = SuperPacket.create(2);
                User user = new User("John", "Doe", 12);
                standardPacket.setValue(1, mapper.writeValueAsString(user), User.class);
                outputStream.write(standardPacket.toByteArray());
                outputStream.flush();

                data = readInput(inputStream);
                packet = SuperPacket.parse(data);
                System.out.println(mapper.readValue(packet.getValue(1, String.class), User.class));

                SuperPacket goodbye = SuperPacket.create(4);

                outputStream.write(goodbye.toByteArray());
                outputStream.flush();

                data = readInput(inputStream);
                SuperPacket bye = SuperPacket.parse(data);

                if (bye.getType() == 4) {
                    System.out.println("bye");
                    synchronized (serverPool) {
                        serverPool.notifyAll();
                    }

                    break;
                }

            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
