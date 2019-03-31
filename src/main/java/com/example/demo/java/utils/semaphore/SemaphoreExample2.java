package com.example.demo.java.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample2 {

    public static void main(String[] args) throws InterruptedException {
        // 统一时间可获取许可证的线程数量，获取不到的都将处于waiting状态
        final Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " in");
                    try {
                          // 拿取2个许可证
                          // semaphore.acquire(2);
                          // 默认1个
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " Get the semaphore.");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放2个许可证
                        // semaphore.release(2);
                        // 默认1个
                        semaphore.release();
                    }
                    System.out.println(Thread.currentThread().getName() + " out");
                }
            }.start();
        }

        while (true) {
            // 当前可用的许可证数量
            System.out.println("AP -> " + semaphore.availablePermits());
            // 评估当前在等待的线程数量
            System.out.println("QL -> " + semaphore.getQueueLength());
            System.out.println("===================================");

            TimeUnit.SECONDS.sleep(1);
        }
    }
}
