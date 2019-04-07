package com.example.demo.java.executors;

import java.util.concurrent.*;

public class FutureExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // testGet();
        // testGetInterrupt();
        testGetWithTimeOut();
    }

    private static void testGet() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        System.out.println("=====I will be printed quickly.=====");
        Integer result = future.get();
        System.out.println(result);

        /**
         * =====I will be printed quickly.=====
         * 10
         */
    }

    private static void testGetInterrupt() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        System.out.println("=====I will be printed quickly.=====");

        Thread callerThread = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3);
                callerThread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // get()会阻塞，阻塞的时候线程被打断会抛出InteterruptException异常
        Integer result = future.get();
        System.out.println(result);

        /**
         * ===== I will be printed quickly.=====
         * Exception in thread "main" java.lang.InterruptedException
         * 	at java.util.concurrent.FutureTask.awaitDone(FutureTask.java:404)
         * 	at java.util.concurrent.FutureTask.get(FutureTask.java:191)
         * 	at com.example.demo.java.executors.FutureExample1.testGetInterrupt(FutureExample1.java:58)
         * 	at com.example.demo.java.executors.FutureExample1.main(FutureExample1.java:9)
         */
    }

    private static void testGetWithTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("任务继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        System.out.println("=====I will be printed quickly.=====");
        // 超时会抛出TimeoutException异常，但是没有终止任务
        Integer result = future.get(5, TimeUnit.SECONDS);
        System.out.println(result);

        /**
         * =====I will be printed quickly.=====
         * Exception in thread "main" java.util.concurrent.TimeoutException
         * 	at java.util.concurrent.FutureTask.get(FutureTask.java:205)
         * 	at com.example.demo.java.executors.FutureExample1.testGetWithTimeOut(FutureExample1.java:88)
         * 	at com.example.demo.java.executors.FutureExample1.main(FutureExample1.java:10)
         * 任务继续执行
         */
    }
}
