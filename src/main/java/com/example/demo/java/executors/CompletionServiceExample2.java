package com.example.demo.java.executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CompletionServiceExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // testGet();
        testGet2();
    }

    private static void testGet() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        final List<Callable<Integer>> callableList = Arrays.asList(
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

        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);

        List<Future<Integer>> futures = new ArrayList<>();
        callableList.stream().forEach(callable -> {
            futures.add(completionService.submit(callable));
        });

       /* Future<Integer> testFuture = null;
        while((testFuture = completionService.take()) != null) {
            System.out.println(testFuture.get());
        }*/

       // 该方法不是个阻塞的方法
        /*Future<Integer> testFuture = completionService.poll();
        System.out.println(testFuture);*/

        System.out.println(completionService.poll(11, TimeUnit.SECONDS).get());
    }

    private static void testGet2() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Event> completionService = new ExecutorCompletionService<>(service);
        final Event event = new Event(1);
        completionService.submit(new MyTask(event), event);

        System.out.println(completionService.take().get().result);
    }

    private static class MyTask implements Runnable {

        private final Event event;

        private MyTask(Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            sleep(10);
            event.setResult("I AM SUCCESSFULLY.");
        }
    }

    private static class Event {
        final private int eventId;
        private String result;


        private Event(int eventId) {
            this.eventId = eventId;
        }

        public int getEventId() {
            return eventId;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
