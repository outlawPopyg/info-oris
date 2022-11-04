package org.example;

import org.example.base.AwesomePacket;
import org.example.test.TestClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AwesomeClient client = AwesomeClient.initConnection("localhost", 4444);


        AwesomePacket packet1 = AwesomePacket.create(1);
        packet1.setValue(1, "Im your client");
        client.sendMessage(packet1);

        AwesomePacket packet2 = AwesomePacket.create(1);
        packet2.setValue(1, "Hello!");
        client.sendMessage(packet2);

        AwesomePacket packet3 = AwesomePacket.create(1);
        packet3.setValue(1, "I'm done");
        client.sendMessage(packet3);

        AwesomePacket endPacket = AwesomePacket.create(2);
        client.sendMessage(endPacket);

//        TestClient.sendMessage(4444, "127.0.0.1", packet);
    }
}
