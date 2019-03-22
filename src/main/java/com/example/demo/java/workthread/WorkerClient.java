package com.example.demo.java.workthread;

public class WorkerClient {
    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorker();

        new TransportThread("AAA", channel).start();
        new TransportThread("BBB", channel).start();
        new TransportThread("CCC", channel).start();

    }
}
