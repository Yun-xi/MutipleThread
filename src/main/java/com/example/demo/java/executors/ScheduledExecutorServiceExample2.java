package com.example.demo.java.executors;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledExecutorServiceExample2 {

    public static void main(String[] args) throws InterruptedException {
        // test1();
        test2();

    }

    /**
     * 在上个任务执行完成之后延迟delay时间执行
     */
    private static void testScheduleWithFixedDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        final AtomicLong interval = new AtomicLong(0L);
        ScheduledFuture<?> scheduledFuture =
                executor.scheduleWithFixedDelay(
                        () -> {
                            long currentTimeMillis = System.currentTimeMillis();
                            if (interval.get() == 0) {
                                System.out.printf("The first time trigger task at %d\n", currentTimeMillis);
                            } else {
                                System.out.printf("The actually spend [%d]\n", currentTimeMillis - interval.get());
                            }

                            interval.set(currentTimeMillis);
                            System.out.println(Thread.currentThread().getName());

                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },
                        1,
                        2,
                        TimeUnit.SECONDS);
    }


    /**
     * setContinueExistingPeriodicTasksAfterShutdownPolicy(true)继续执行之前的定时任务
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println(executor.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        final AtomicLong interval = new AtomicLong(0L);
        ScheduledFuture<?> scheduledFuture =
                executor.scheduleWithFixedDelay(
                        () -> {
                            long currentTimeMillis = System.currentTimeMillis();
                            if (interval.get() == 0) {
                                System.out.printf("The first time trigger task at %d\n", currentTimeMillis);
                            } else {
                                System.out.printf("The actually spend [%d]\n", currentTimeMillis - interval.get());
                            }

                            interval.set(currentTimeMillis);
                            System.out.println(Thread.currentThread().getName());

                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },
                        1,
                        2,
                        TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1200);
        executor.shutdown();
        System.out.println("===OVER===");
    }

    private static void test2() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println(executor.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        final AtomicLong interval = new AtomicLong(0L);
        ScheduledFuture<?> scheduledFuture =
                executor.scheduleWithFixedDelay(
                        () -> {
                            long currentTimeMillis = System.currentTimeMillis();
                            if (interval.get() == 0) {
                                System.out.printf("The first time trigger task at %d\n", currentTimeMillis);
                            } else {
                                System.out.printf("The actually spend [%d]\n", currentTimeMillis - interval.get());
                            }

                            interval.set(currentTimeMillis);
                            System.out.println(Thread.currentThread().getName());

                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },
                        1,
                        2,
                        TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1200);
        executor.shutdown();
        System.out.println("===OVER===");

        /**
         * true
         * The first time trigger task at 1554468170627
         * pool-1-thread-1
         * ===OVER===
         */
    }
}
