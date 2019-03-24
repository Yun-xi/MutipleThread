package com.example.demo.java.design_pattern.guarded;

public class SuspensionClient {
    public static void main(String[] args) {
        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "AAA").start();
        new ServerThread(queue).start();
    }
}
