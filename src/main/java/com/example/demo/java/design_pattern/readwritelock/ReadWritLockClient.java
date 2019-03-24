package com.example.demo.java.design_pattern.readwritelock;

public class ReadWritLockClient {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData(10);
        ReaderWorker readerWorker1 = new ReaderWorker(sharedData);
        ReaderWorker readerWorker2 = new ReaderWorker(sharedData);
        WriterWorker writerWorker1 = new WriterWorker(sharedData, "A");
        WriterWorker writerWorker2 = new WriterWorker(sharedData, "B");
        readerWorker1.start();
        readerWorker2.start();
        writerWorker1.start();
        writerWorker2.start();
    }
}
