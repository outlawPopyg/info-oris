package datagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client {

    public static final int port = 4444;
    public static final String host = "localhost";

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try (DatagramSocket client = new DatagramSocket()) {
            while (true) {
                System.out.print("[CLIENT] Enter message:\n > ");
                String s = in.readLine();
                byte[] data = s.getBytes();

                DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getByName(host), port);
                client.send(outPacket);

                byte[] buffer = new byte[65536];
                DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

                client.receive(inPacket);
                String response = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println(response);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

class ClientExample {
    public static void main(String[] args) {
        DatagramSocket sock = null;

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

        try {
            sock = new DatagramSocket();

            while(true) {
                //Ожидаем ввод сообщения серверу
                System.out.println("Введите сообщение серверу: ");
                String s = cin.readLine();
                byte[] b = s.getBytes();

                // Отправляем сообщение
                DatagramPacket  dp = new DatagramPacket(b , b.length , InetAddress.getByName("localhost") , 7000);
                sock.send(dp);

                // буфер для получения входящих данных
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                // Получаем данные
                sock.receive(reply);
                byte[] data = reply.getData();
                s = new String(data, 0, reply.getLength());

                System.out.println("Сервер: " + reply.getAddress().getHostAddress() + ", порт: " + reply.getPort() + ", получил: " + s);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

}
