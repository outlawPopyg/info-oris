package nioExamples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class ClientSocketChannel {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        SocketAddress socketAddr = new InetSocketAddress("localhost", 9000);
        channel.connect(socketAddr);

        while (true) {
            int readyChannels = selector.selectNow();

            if (readyChannels == 0) continue;

            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();

                if(key.isAcceptable()) {
                    System.out.println("isAcceptable");
                } else if (key.isConnectable()) {
                    System.out.println("isConnectable");
                } else if (key.isReadable()) {
                    System.out.println("isReadable");
                } else if (key.isWritable()) {
                    System.out.println("isWritable");
                }

                keyIterator.remove();
            }
        }

//        ByteBuffer buf = ByteBuffer.allocate(48);
//
//        int bytesRead = client.read(buf);
//        System.out.println(new String(buf.array(), 0, bytesRead));
//
//        client.close();
    }
}
