package com.example.demo.java.executors;

import java.sql.Time;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledExecutorServiceExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

        /*ScheduledFuture<?> future = executor.schedule(() -> System.out.println("=== I will be invoked! ==="), 2, TimeUnit.SECONDS);

        System.out.println(future.cancel(true));*/

        /*ScheduledFuture<Integer> future = executor.schedule(() -> 2, 2, TimeUnit.SECONDS);

        System.out.println(future.get());*/

        final AtomicLong interval = new AtomicLong(0L);
        ScheduledFuture<?> scheduledFuture =
                executor.scheduleAtFixedRate(
                        () -> {
                            long currentTimeMillis = System.currentTimeMillis();
                            if (interval.get() == 0) {
                                System.out.printf("The first time trigger task at %d\n", currentTimeMillis);
                            } else {
                                System.out.printf("The actually spend [%d]\n", currentTimeMillis - interval.get());
                            }
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            interval.set(currentTimeMillis);
                            System.out.println(Thread.currentThread().getName());
                        },
                        1,
                        2,
                        TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
        System.out.println(executor.getActiveCount());
    }

}
