package com.example.demo.java.executors;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-04-04 15:54
 */
public class ExecutorServiceExample1 {

    public static void main(String[] args) throws InterruptedException {
        // testAbortPolicy();
        // testDiscardPolicy();
        testCallerRunsPolicy();
    }

    private static void testCallerRunsPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);
        executorService.execute(() -> {
            System.out.println("=============== " + Thread.currentThread().getName() + " =============");
            System.out.println("x");
        });
    }

    private static void testDiscardPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(1);
        executorService.execute(() -> System.out.println("x"));
    }

    private static void testAbortPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);
        executorService.execute(() -> System.out.println("x"));
    }
}
