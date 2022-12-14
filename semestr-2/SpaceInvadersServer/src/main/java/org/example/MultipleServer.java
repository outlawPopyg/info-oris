package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultipleServer {
    private ServerSocket serverSocket;

    private static Map<String, Socket> socketMap = new HashMap<>();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private ObjectMapper objectMapper;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }


        @Override
        public void run() {
            try {
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                objectMapper = new ObjectMapper();

                SuperPacket packet = SuperPacket.parse(readInput(inputStream));
                Message message = objectMapper.readValue(packet.getValue(1, String.class), Message.class);
                socketMap.put(message.getSessionId(), clientSocket);

                for (String key : socketMap.keySet()) {
                    if (!key.equals(message.getSessionId())) {
                        socketMap.get(key).getOutputStream().write(0);
                        socketMap.get(key).getOutputStream().flush();
                    }

                }

                inputStream.close();
                outputStream.close();
                clientSocket.close();

            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
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
    }

}
