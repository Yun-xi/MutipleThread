package com.example.demo.java.design_pattern.observer;

public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject){
        super(subject);
    }
    @Override
    public void update() {
        System.out.println("Binary String: " + Integer.toBinaryString(subject.getState()));
    }
}
