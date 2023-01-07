package datagram;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Server {

    public static final int port = 4444;

    public static void main(String[] args) {
        try (DatagramSocket listener = new DatagramSocket(port)) {

            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            System.out.println("[SERVER] Waiting for the data ...");

            while (true) {
                listener.receive(incoming);
                byte[] receivedData = incoming.getData();
                String receivedString = new String(receivedData, 0, incoming.getLength());

                System.out.println("[SERVER] Received: " + receivedString);

                byte[] resp = receivedString.getBytes();
                DatagramPacket response = new DatagramPacket(resp, resp.length, incoming.getAddress(), incoming.getPort());
                listener.send(response);
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}


class ServerExample {
    public static void main(String[] args) {
        try {
            // Создаем сокет
            DatagramSocket sock = new DatagramSocket(7000);

            // буфер для получения входящих данных
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            System.out.println("Ожидаем данные...");

            while (true) {
                // Получаем данные
                sock.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());

                System.out.println("Сервер получил: " + s);

                // Отправляем данные клиенту
                DatagramPacket dp = new DatagramPacket(s.getBytes() , s.getBytes().length , incoming.getAddress() , incoming.getPort());
                sock.send(dp);
            }
        }

        catch(IOException e) {
            System.err.println("IOException " + e);
        }
    }


}