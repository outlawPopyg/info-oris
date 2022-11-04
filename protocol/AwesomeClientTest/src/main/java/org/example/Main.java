package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AwesomeClient client = new AwesomeClient(4444, "127.0.0.1");
        client.sendMessage("hi server");
    }
}
