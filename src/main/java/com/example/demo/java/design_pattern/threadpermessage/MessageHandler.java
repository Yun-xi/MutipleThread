package com.example.demo.java.design_pattern.threadpermessage;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandler {
    private final static Random random= new Random(System.currentTimeMillis());

    private final static Executor executor = Executors.newFixedThreadPool(5);

    private void request(Message message) {
        executor.execute(() -> {
            String value = message.getValue();
            try {
                Thread.sleep(random.nextInt(1000));
                System.out.println("The message will be handle by " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        ((ExecutorService)executor).shutdown();
    }
}
