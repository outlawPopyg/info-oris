package org.example;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        AwesomeServer server = AwesomeServer.create(4444, serverPool);

        while (true) {
            synchronized (serverPool) {
                serverPool.execute(server);

                if (serverPool.getActiveCount() >= 10) {
                    serverPool.wait();
                }
            }
        }
    }
}
