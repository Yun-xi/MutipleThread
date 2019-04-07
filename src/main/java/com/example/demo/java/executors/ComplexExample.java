package com.example.demo.java.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ComplexExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /*final ExecutorService service = Executors.newFixedThreadPool(5);

        List<Runnable> tasks = IntStream.range(0, 5).boxed().map(ComplexExample::toTask).collect(Collectors.toList());

        final CompletionService<Object> completionService = new ExecutorCompletionService<Object>(service);

        tasks.forEach(r -> {
            completionService.submit(r, null);
        });

        Future<?> future;
        while ((future=completionService.take()) != null) {
            System.out.println(future.get());
        }*/

        /**
         * The Task [0] will be executed
         * The Task [4] will be executed
         * The Task [3] will be executed
         * The Task [2] will be executed
         * The Task [1] will be executed
         * The Task [0] execute done
         * null
         * The Task [1] execute done
         * null
         */

        /*final ExecutorService service = Executors.newSingleThreadExecutor();

        List<Runnable> tasks = IntStream.range(0, 5).boxed().map(ComplexExample::toTask).collect(Collectors.toList());

        final CompletionService<Object> completionService = new ExecutorCompletionService<Object>(service);

        tasks.forEach(r -> {
            completionService.submit(r, null);
        });

        TimeUnit.SECONDS.sleep(12);
        List<Runnable> runnables = service.shutdownNow();
        System.out.println(runnables);*/

        ExecutorService service = Executors.newSingleThreadExecutor();
        List<Callable<Integer>> tasks = IntStream.range(0, 5).boxed().map(MyTask::new).collect(toList());
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);

        tasks.forEach(completionService::submit);
        TimeUnit.SECONDS.sleep(12);
        service.shutdownNow();

        tasks.stream().filter(callable -> !((MyTask)callable).isSuccess()).forEach(System.out::println);
    }

    private static class MyTask implements Callable<Integer> {

        private final Integer value;

        private boolean success = false;

        private MyTask(Integer value) {
            this.value = value;
        }

        @Override
        public Integer call() throws Exception {
            System.out.printf("The Task [%d] will be executed\n", value);
            TimeUnit.SECONDS.sleep(value * 5 + 10);
            System.out.printf("The Task [%d] execute done\n", value);
            success = true;
            return value;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    private static Runnable toTask(int i) {
        return () -> {
            try {
                System.out.printf("The Task [%d] will be executed\n", i);
                TimeUnit.SECONDS.sleep(i * 5 + 10);
                System.out.printf("The Task [%d] execute done\n", i);
            } catch (InterruptedException e) {
                System.out.printf("The Task [%d] be interrupted\n", i);
            }
        };
    }
}
