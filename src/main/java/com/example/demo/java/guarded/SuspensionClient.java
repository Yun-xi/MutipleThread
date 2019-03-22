package com.example.demo.java.guarded;

public class SuspensionClient {
    public static void main(String[] args) {
        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "AAA").start();
        new ServerThread(queue).start();
    }
}
