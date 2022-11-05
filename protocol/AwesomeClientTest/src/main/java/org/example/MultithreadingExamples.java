package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultithreadingExamples {
    public static void entryPoint() throws InterruptedException {
        System.out.println("debug");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            System.out.println("Hello world");
        });

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Pool size:" + " " + pool.getPoolSize() + ", Active:" + " " + pool.getActiveCount());

        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Pool size:" + " " + pool.getPoolSize() + ", Active:" + " " + pool.getActiveCount());

        Thread.sleep(1000);
        System.out.println("Pool size:" + " " + pool.getPoolSize() + ", Active:" + " " + pool.getActiveCount());
        System.out.println("end");
    }
}













