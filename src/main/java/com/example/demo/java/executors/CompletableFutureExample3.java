package com.example.demo.java.executors;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFutureExample3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        future.thenRun(() -> System.out.println("done")).thenRunAsync(() -> System.out.println("done"));
        System.out.println(future.get());
        /*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(
                (Supplier<String>) () -> {
                    throw new RuntimeException("not get the data");
                }).handleAsync((s, t) -> {
            Optional.of(t).ifPresent(e -> System.out.println("Error"));
            return (s == null) ? 0 : s.length();
        });
        System.out.println(future.get());*/
                /*.thenApplyAsync(s -> {
                    try {
                        System.out.println("=============");
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("===over===");
                    return s.length();
                })
                ;*/
//                .whenComplete((v, t) -> System.out.println("Done"));
        /*future.whenCompleteAsync((v, t) -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("== over ===");
        });
        System.out.println(future.get());
        System.out.println("===Finished===");*/
        Thread.currentThread().join();
    }
}
