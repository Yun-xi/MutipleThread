package com.example.demo.java.collections.blocking;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueExample {

    /**
     * FIFO(First in First out)
     * @param size
     * @param <T>
     * @return
     */
    public <T> ArrayBlockingQueue<T> create(int size) {
        return new ArrayBlockingQueue<>(size);
    }
}
