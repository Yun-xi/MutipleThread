package com.example.demo.java.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample4 {

    public static void main(String[] args) throws InterruptedException {
        // acceptEither();
        // runAfterBoth();
        // runAfterEither();
        // combine();
        // compose();
        apply();
        Thread.currentThread().join();
    }

    private static void apply() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the compose");
            sleep(5);
            System.out.println("end the compose");
            return "compose-1";
        }).thenApply(s -> {
            System.out.println("start the compose2");
            sleep(3);
            System.out.println("end the compose2");
            return s.length();
        }).thenAccept(System.out::println);
        /**
         * start the compose
         * end the compose
         * start the compose2
         * end the compose2
         * 9
         */
    }

    private static void compose() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the compose");
            sleep(5);
            System.out.println("end the compose");
            return "compose-1";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println("start the compose2");
            sleep(3);
            System.out.println("end the compose2");
            return s.length();
        })).thenAccept(System.out::println);
        /**
         * start the compose
         * end the compose
         * start the compose2
         * end the compose2
         * 9
         */
    }

    private static void combine() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the combine1");
            sleep(5);
            System.out.println("end the combine1");
            return "combine-1";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the combine2");
            sleep(5);
            System.out.println("end the combine2");
            return 100;
        }), (s, i) -> s.length() > i).whenComplete((v, t) -> System.out.println(v));
    }

    private static void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth1");
            sleep(5);
            System.out.println("end the runAfterBoth1");
            return "runAfterBoth1";
        }).runAfterEitherAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth2");
            sleep(3);
            System.out.println("end the runAfterBoth2");
            return 100;
        }), () -> System.out.println("DONE"));
    }

    private static void runAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth1");
            sleep(5);
            System.out.println("end the runAfterBoth1");
            return "runAfterBoth1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth2");
            sleep(5);
            System.out.println("end the runAfterBoth2");
            return 100;
        }), () -> System.out.println("DONE"));
    }

    private static void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the acceptEither1");
            sleep(5);
            System.out.println("end the acceptEither1");
            return "acceptEither1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the acceptEither2");
            sleep(5);
            System.out.println("end the acceptEither2");
            return "acceptEither2";
        }), System.out::println);
    }

    private static void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync");
            sleep(5);
            System.out.println("end the supplyAsync");
            return "thenAcceptBoth";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the thenAcceptBoth");
            sleep(5);
            System.out.println("end the thenAcceptBoth");
            return 100;
        }), (s, i) -> System.out.println(s + " --- " + i));
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
