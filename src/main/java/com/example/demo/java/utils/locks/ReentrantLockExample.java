package com.example.demo.java.utils.locks;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockExample {

//    private final static Lock lock = new ReentrantLock();
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        /*IntStream.range(0, 2).forEach(i ->
            new Thread(() -> {
                needLock();
            }).start()
        );*/

        /*Thread thread1 = new Thread(() -> testUnInterruptibly());
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(() -> testUnInterruptibly());
        thread2.start();
        TimeUnit.SECONDS.sleep(1);

        thread2.interrupt();
        System.out.println("=========================");*/

        /*Thread thread1 = new Thread(() -> testUnInterruptibly());
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(() -> testUnInterruptibly());
        thread2.start();*/

        Thread thread1 = new Thread(() -> testUnInterruptibly());
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(() -> testUnInterruptibly());
        thread2.start();
        TimeUnit.SECONDS.sleep(1);

        // 获取在等待锁的Thread的个数
        Optional.of(lock.getQueueLength()).ifPresent(System.out::println);
        // 获取是否有在等待锁的Thread
        Optional.of(lock.hasQueuedThreads()).ifPresent(System.out::println);
        // 线程是否在等待
        Optional.of(lock.hasQueuedThread(thread1)).ifPresent(System.out::println);
        Optional.of(lock.hasQueuedThread(thread2)).ifPresent(System.out::println);
        // 该锁是否被其他线程获取
        Optional.of(lock.isLocked()).ifPresent(System.out::println);

    }

    public static void testTryLock() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " get lock");
                while (true) {

                }
            } finally {
                lock.unlock();
            }
        } else {
            // 执行其他操作
            System.out.println(Thread.currentThread().getName() + " not get lock");
        }
    }

    public static void testUnInterruptibly() {

        try {
            // 不可被打断
            // lock.lock();
            // 可被打断
            lock.lockInterruptibly();
            // 获取当前线程获取该锁的个数
            Optional.of(Thread.currentThread().getName() + " : " + lock.getHoldCount()).ifPresent(System.out::println);
            System.out.println(Thread.currentThread().getName() + " get lock");
            while (true) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void needLock() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get lock");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
