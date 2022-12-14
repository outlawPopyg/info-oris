package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AwesomeServer implements Runnable {
    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ThreadPoolExecutor serverPool;

    private InputStream inputStream;
    private OutputStream outputStream;

    private ObjectMapper mapper = new ObjectMapper();

    private Map<Integer, Socket> socketMap = new HashMap<>();
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

            System.out.println("connected" + " " + clientSocket.toString());
            socketMap.put(clientSocket.getLocalPort(), clientSocket);

            SuperPacket handshake = SuperPacket.create(1);
            outputStream.write(handshake.toByteArray());
            outputStream.flush();

            while (true) {
                Runnable thread = () -> {
                    try {
                        System.out.println(Arrays.toString(readInput(inputStream)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

                thread.run();

//                if (bye.getType() == 4) {
//                    System.out.println("bye");
//                    synchronized (serverPool) {
//                        serverPool.notifyAll();
//                    }
//
//                    break;
//                }

            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
