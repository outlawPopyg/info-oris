package org.example;

import org.example.base.AwesomePacket;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        AwesomeClient client = AwesomeClient.initConnection("localhost", 4444);

        AwesomePacket packet1 = AwesomePacket.create(1);
        packet1.setValue(1, 1);
        packet1.setValue(2, new String[] {"Hello world"});
        client.sendMessage(packet1);

        AwesomePacket packet2 = AwesomePacket.create(1);
        packet2.setValue(1, 5);
        packet2.setValue(2, new String[] {"Good bye world"});
        client.sendMessage(packet2);


        AwesomePacket endPacket = AwesomePacket.create(2);
        client.sendMessage(endPacket);
    }
}
