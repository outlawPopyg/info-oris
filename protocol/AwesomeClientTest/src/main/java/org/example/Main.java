package org.example;

import org.example.base.AwesomePacket;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AwesomePacket packet = AwesomePacket.create(1);
        packet.setValue(1, 123);
        packet.setValue(2, true);
        packet.setValue(3, "I'm client");

        AwesomeClient.sendMessage(4444, "127.0.0.1", packet);
    }
}
