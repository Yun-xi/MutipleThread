package com.example.demo.java.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorBuild {

    public static void main(String[] args) {
        ExecutorService executorService = buildThreadPoolExecutor();
    }

    /**
     * corePoolSize：线程池的核心线程数，默认情况下，核心线程数会一直在线程池中存活，即使它们处理闲置状态。如果将ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，那么闲置的核心线程在等待新任务到来时会执行超时策略，这个时间间隔由keepAliveTime所指定，当等待时间超过keepAliveTime所指定的时长后，核心线程就会被终止。
     *
     * maximumPoolSize：线程池所能容纳的最大线程数量，当活动线程数到达这个数值后，后续的新任务将会被阻塞。
     *
     * keepAliveTime：非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收。当ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true时，keepAliveTime同样会作用于核心线程。
     *
     * unit：用于指定keepAliveTime参数的时间单位，这是一个枚举，常用的有TimeUnit.MILLISECONDS(毫秒)，TimeUnit.SECONDS(秒)以及TimeUnit.MINUTES(分钟)等。
     *
     * workQueue：线程池中的任务队列，通过线程池的execute方法提交Runnable对象会存储在这个队列中。
     *
     * workQueue：BlockingQueue是双缓冲队列。BlockingQueue内部使用两条队列，允许两个线程同时向队列一个存储，一个取出操作。在保证并发安全的同时，提高了队列的存取效率。
     *  常用的几种BlockingQueue：
     *      ArrayBlockingQueue（int i）:规定大小的BlockingQueue，其构造必须指定大小。其所含的对象是FIFO顺序排序的。
     *      LinkedBlockingQueue（）或者（int i）:大小不固定的BlockingQueue，若其构造时指定大小，生成的BlockingQueue有大小限制，不指定大小，其大小有Integer.MAX_VALUE来决定。其所含的对象是FIFO顺序排序的。
     *      PriorityBlockingQueue（）或者（int i）:类似于LinkedBlockingQueue，但是其所含对象的排序不是FIFO，而是依据对象的自然顺序或者构造函数的Comparator决定。
     *      SynchronizedQueue（）:特殊的BlockingQueue，对其的操作必须是放和取交替完成。
     *
     * threadFactory：线程工厂，为线程池提供创建新线程的功能。ThreadFactory是一个接口，它只有一个方法：Thread newThread（Runnable r）。
     *
     * RejectExecutionHandler：这个参数表示当ThreadPoolExecutor已经关闭或者ThreadPoolExecutor已经饱和时（达到了最大线程池大小而且工作队列已经满），execute方法将会调用Handler的rejectExecution方法来通知调用者，默认情况 下是抛出一个RejectExecutionException异常。
     * @return
     */
    private static ExecutorService buildThreadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("==The ThreadPoolExecutor create done.");
        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));
        return executorService;
    }

    private static void sleepSeconds(long seconds) {
        try {
            System.out.println("* " + Thread.currentThread().getName() + " *");
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
