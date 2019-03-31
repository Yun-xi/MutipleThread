package com.example.demo.java.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.IntStream;

public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) {
        final AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        final TestMe me = new TestMe();

        IntStream.range(0, 2).forEach(i ->
                new Thread(() -> {
                    final int MAX = 20;
                    IntStream.range(0, MAX).forEach(j -> {
                        int v = updater.getAndIncrement(me);
                        System.out.println(Thread.currentThread().getName() + "=>" + v);
                    });
                }).start()
        );
    }
}
