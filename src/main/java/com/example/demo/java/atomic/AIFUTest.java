package com.example.demo.java.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AIFUTest {

    private volatile int i;

    private AtomicIntegerFieldUpdater<AIFUTest> updater = AtomicIntegerFieldUpdater.newUpdater(AIFUTest.class, "i");

    public void update(int newValue) {
        updater.compareAndSet(this, i, newValue);
    }

    public int get() {
        return i;
    }

    public static void main(String[] args) {
        AIFUTest test = new AIFUTest();
        test.update(10);
        System.out.println(test.get());
    }
}
