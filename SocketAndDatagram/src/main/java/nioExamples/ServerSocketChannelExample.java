package nioExamples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;

public class ServerSocketChannelExample {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("[SERVER] Waiting for connection");
        SocketChannel client = serverSocket.accept();
        System.out.println("[SERVER] Connection Set:  " + client.getRemoteAddress());

        while (true) {

            System.out.println("Enter data: \n > ");
            String data = in.readLine();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(data.getBytes());

            buffer.flip();

            while (buffer.hasRemaining()) {
                client.write(buffer);
            }

        }
    }
}
