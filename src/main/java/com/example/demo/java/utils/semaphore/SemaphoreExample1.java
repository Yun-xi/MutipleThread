package com.example.demo.java.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample1 {

    public static void main(String[] args) {
        final SemaphoreLock lock = new SemaphoreLock();

        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is running");
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " get the #SemaphoreLock");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    System.out.println(Thread.currentThread().getName() + " release the #SemaphoreLock");
                }
            }.start();
        }
    }

    static class SemaphoreLock {
        private final Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException {
            // 获取一个许可证
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }
}
