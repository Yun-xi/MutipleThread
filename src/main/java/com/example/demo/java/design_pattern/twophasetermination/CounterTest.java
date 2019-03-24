package com.example.demo.java.design_pattern.twophasetermination;

public class CounterTest {
    public static void main(String[] args) throws InterruptedException {
        CounterIncrement counterIncrement = new CounterIncrement();
        counterIncrement.start();

        Thread.sleep(10000);
        counterIncrement.close();
    }

}
