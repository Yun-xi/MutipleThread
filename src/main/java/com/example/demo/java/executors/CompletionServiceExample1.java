package com.example.demo.java.executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CompletionServiceExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // futureDefect1();
        futureDefect2();
    }

    /**
     * no callback
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void futureDefect1() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        System.out.println("======================");
        future.get();
    }

    private static void futureDefect2() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> callableList = Arrays.asList(
                () -> {
                    sleep(10);
                    System.out.println("The 10 finished");
                    return 10;
                },
                () -> {
                    sleep(20);
                    System.out.println("The 20 finished");
                    return 20;
                }
                );
        List<Future<Integer>> futureList = new ArrayList<>();
        futureList.add(service.submit(callableList.get(0)));
        futureList.add(service.submit(callableList.get(1)));
        futureList.stream().forEach(future ->
        {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        /*List<Future<Integer>> futureList = service.invokeAll(callableList);

        Integer result1 = futureList.get(0).get();
        System.out.println(result1);

        Integer result2 = futureList.get(1).get();
        System.out.println(result2);*/

        /**
         * The 10 finished
         * The 20 finished
         * 10
         * 20
         */
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

