package com.example.demo.java.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FutureExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // testIsDone();
        testCancel();
        // testCancel2();
    }

    /**
     * ompletion may be due to normal termination, an exception, or
     * cancellation
     */
    private static void testIsDone() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        System.out.println(future.isDone());

        Integer result = future.get();
        System.out.println(result);
        // 当任务返回了、出现异常或者取消了的时候返回true
        System.out.println(future.isDone());

        /**
         * false
         * 10
         * true
         */
    }

    private static void testCancel() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = executorService.submit(() -> {
           /* try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           while (running.get()) {
               System.out.println("======执行======");
           }
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(1);
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }

    private static void testCancel2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = executorService.submit(() -> {
           /*try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            /*while (!Thread.interrupted()) {

            }*/
            System.out.println("11111");
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }

}
