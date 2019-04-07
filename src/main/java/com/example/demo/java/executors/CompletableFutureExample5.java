package com.example.demo.java.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample5 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // getNow();
        // complete();
        // testJoin();
        // completeExceptionally();
        // obtrudeException();
        CompletableFuture<String> future = errorHandle();
        future.whenComplete((v, t) -> System.out.println(v));
        Thread.currentThread().join();
    }

    private static CompletableFuture<String> errorHandle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("======== I will be still process ==========");
            return "hello";
        });

        future.thenApply(s -> {
            Integer.parseInt(s);
            sleep(10);
            System.out.println("==========Keep Move===========");
            return s + " world";
        }).exceptionally(Throwable::getMessage).thenAccept(System.out::println);

        return future;
    }


    private static void obtrudeException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("======== I will be still process ==========");
            return "hello";
        });
        future.obtrudeException(new Exception("I am error."));
        System.out.println(future.get());
    }

    private static void completeExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            sleep(5);
            System.out.println("======== I will be still process ==========");
            return "hello";
        });
        sleep(1);
        future.completeExceptionally(new RuntimeException());
        System.out.println(future.get());
    }

    private static void testJoin() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("======== I will be still process ==========");
            return "hello";
        });
        String result = future.join();
        System.out.println(result);
    }

    private static void complete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("======== I will be still process ==========");
            return "hello";
        });

        sleep(1);
        /**
         * 注意：如果该complete执行的时候CompletableFuture里面的方法还没有执行，就被被cancle掉
         * 如果执行了，就会继续执行
         */
        boolean finished = future.complete("world");
        System.out.println(finished);
        System.out.println(future.get());
    }

    private static void getNow() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "hello";
        });

        String result = future.getNow("world");

        System.out.println(result);
        System.out.println(future.get());
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
