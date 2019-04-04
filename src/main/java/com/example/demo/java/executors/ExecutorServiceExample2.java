package com.example.demo.java.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-04-04 17:05
 */
public class ExecutorServiceExample2 {

    public static void main(String[] args) throws InterruptedException {
        // test();
        // testAllowCoreThreadTimeOut();
        // testRemove();
        // testPrestartCoreThread();
        // testPrestartAllThread();
        testThreadPoolAdvice();
    }

    private static void test() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        System.out.println(executorService.getActiveCount());

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(executorService.getActiveCount());

        /**
         * 0
         * 1
         */
    }

    private static void testAllowCoreThreadTimeOut() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);

        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    private static void testRemove() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);

        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("i am finished ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        TimeUnit.MILLISECONDS.sleep(20);
        Runnable r = () -> {
            System.out.println("I will never be executed!");
        };
        executorService.execute(r);
        TimeUnit.MILLISECONDS.sleep(20);
        // 移除任务
        executorService.remove(r);
    }

    private static void testPrestartCoreThread() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        System.out.println(executorService.getActiveCount());

        System.out.println(executorService.prestartCoreThread());
        System.out.println(executorService.getActiveCount());

        System.out.println(executorService.prestartCoreThread());
        System.out.println(executorService.getActiveCount());

        System.out.println(executorService.prestartCoreThread());
        System.out.println(executorService.getActiveCount());

        /**
         * 0
         * true
         * 1
         * true
         * 2
         * false
         * 2
         */
    }

    private static void testPrestartAllThread() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        executorService.setMaximumPoolSize(3);
        System.out.println(executorService.getActiveCount());

        int num = executorService.prestartAllCoreThreads();
        System.out.println(num);
        System.out.println(executorService.getActiveCount());
        /**
         * 0
         * 2
         * 2
         */

    }

    private static void testThreadPoolAdvice() {
        ExecutorService executorService = new MyThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());

        executorService.execute(new MyRunnable(1) {
            @Override
            public void run() {
                System.out.println(1 / 0);
            }
        });
    }

    private abstract static class MyRunnable implements Runnable {

        private final int no;

        protected MyRunnable(int no) {
            this.no = no;
        }

        protected int getData() {
            return this.no;
        }
    }

    private static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("init the " + ((MyRunnable)r).getData());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (null == t) {
                System.out.println("successful " + ((MyRunnable)r).getData());
            } else {
                t.printStackTrace();
            }
        }
    }
}
