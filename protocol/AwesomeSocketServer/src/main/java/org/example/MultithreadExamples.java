package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultithreadExamples {
    public static void entryPoint() throws InterruptedException {
        System.out.println("debug");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            System.out.println("Hello world!");
        });
        Runnable runnable = new MyRannable();
        executor.submit(runnable);

        MyThread thread = new MyThread();
        executor.submit(thread);

        runnable.run();
        thread.start();

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        System.out.println(String.format("Core thread count: %d; Max threads count: %d",
                pool.getCorePoolSize(), pool.getMaximumPoolSize()));
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(String.format("Pool size: %d; Active: %d", pool.getPoolSize(), pool.getActiveCount()));
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(String.format("Pool size: %d; Active: %d", pool.getPoolSize(), pool.getActiveCount()));


        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread.sleep(2000);
        System.out.println(String.format("Pool size: %d; Active: %d", pool.getPoolSize(), pool.getActiveCount()));

        System.out.println("end");

    }

    public static class MyRannable implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello world!");
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello world!");
            super.run();
        }
    }


}
