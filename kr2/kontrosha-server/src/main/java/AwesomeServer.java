import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
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
            Scanner sc = new Scanner(System.in);
            Integer number;

            System.out.println("Загадайте число");
            number = sc.nextInt();

            SuperPacket packet = SuperPacket.create(2);
            String codingMessage = CezarCod.getCodingMessage("wondered", 44);
            packet.setValue(1, mapper.writeValueAsString(new Message(codingMessage, 44)), Message.class);

            outputStream.write(packet.toByteArray());
            outputStream.flush();

            while (true) {
                byte[] data = readInput(inputStream);
                SuperPacket response = SuperPacket.parse(data);
                String guess = response.getValue(1, String.class);
                char sign = guess.charAt(0);
                int guessNumber = Integer.parseInt(guess.substring(1));

                SuperPacket yesPacket = SuperPacket.create(5);
                SuperPacket noPacket = SuperPacket.create(6);
                SuperPacket endGamePacket = SuperPacket.create(7);

                switch (sign) {
                    case '>':
                        if (number > guessNumber) {
                            outputStream.write(yesPacket.toByteArray());
                            outputStream.flush();
                        } else {
                            outputStream.write(noPacket.toByteArray());
                            outputStream.flush();
                        }
                        break;

                    case '<':
                        if (number < guessNumber) {
                            outputStream.write(yesPacket.toByteArray());
                            outputStream.flush();
                        } else {
                            outputStream.write(noPacket.toByteArray());
                            outputStream.flush();
                        }
                        break;

                    case '=':
                        if (guessNumber == number) {
                            outputStream.write(endGamePacket.toByteArray());
                            outputStream.flush();
                            System.exit(1);
                        } else {
                            outputStream.write(noPacket.toByteArray());
                            outputStream.flush();
                        }
                        break;
                }

            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
