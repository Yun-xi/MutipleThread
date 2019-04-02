package com.example.demo.java.utils.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

public class StampedLockExample1 {

    private final static StampedLock lock = new StampedLock();

    private final static List<Long> DATA = new ArrayList<>();

    public static void main(String[] args) {

        final ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable readTask = () -> {
            for (;;) {
                read();
            }
        };

        Runnable writeTask = () -> {
            for (;;) {
                write();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(writeTask);

    }

    /**
     * 乐观读锁
     */
    private static void read() {
        // 获得一个乐观读锁
        long stamp = lock.tryOptimisticRead();
        // 检查乐观读锁后是否有其他写锁发生，有则返回false
        if (!lock.validate(stamp)) {// 1.判断这个stamp是否在读过程发生期间被修改过,如果stamp没有被修改过,则认为这次读取时有效的,因此就可以直接return了,反之,
            // 如果stamp是不可用的,则意味着在读取的过程中,可能被其他线程改写了数据,因此,有可能出现脏读,如果如果出现这种情况,我们可以像CAS操作那样在一个死循环中一直使用乐观锁,知道成功为止
            try {
                // 获取一个悲观读锁
                stamp = lock.readLock(); // 2.也可以升级锁的级别,这里我们升级乐观锁的级别,将乐观锁变为悲观锁, 如果当前对象正在被修改,则读锁的申请可能导致线程挂起.
                System.out.println(stamp);
                Optional.of(
                        DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))
                ).ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放悲观读锁
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * 悲观读锁
     */
    /*private static void read() {
        long stamped = lock.readLock();
        try {
            Optional.of(
                DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))
            ).ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamped);
        }
    }*/

    private static void write() {
        long stamped = lock.writeLock();
        try {
            DATA.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }
}
