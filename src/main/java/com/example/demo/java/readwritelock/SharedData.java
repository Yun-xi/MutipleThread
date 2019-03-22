package com.example.demo.java.readwritelock;

import java.util.stream.IntStream;

public class SharedData {

    private final char[] buffer;

    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];
        IntStream.range(0, size).forEach(i ->
            this.buffer[i] = '*'
        );
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return this.doRead();
        } finally {
            lock.readUnlock();
        }
    }

    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            this.doWrite(c);
        } finally {
            lock.writeUnlock();
        }
    }

    private void doWrite(char c) {
        IntStream.range(0, buffer.length).forEach(i -> {
                    buffer[i] = c;
                    slowly(10);
                }
        );
    }

    private char[] doRead() {
        char[] newBuf = new char[buffer.length];
        IntStream.range(0, buffer.length).forEach(i ->
                newBuf[i] = buffer[i]
        );
        slowly(50);
        return newBuf;
    }

    private void slowly(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
