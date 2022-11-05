package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AwesomeServer server = AwesomeServer.create(4444);
        server.start();
    }
}
