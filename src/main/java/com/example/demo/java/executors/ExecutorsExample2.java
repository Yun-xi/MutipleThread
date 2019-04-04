package com.example.demo.java.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-04-04 15:24
 */
public class ExecutorsExample2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();
        List<Callable<String>> callableList = IntStream.rangeClosed(1, 20).boxed().map(i ->
                (Callable<String>) () -> {
                    System.out.println("Thread " + Thread.currentThread().getName());
                    sleep(10);
                    return "Task-" + i;
                }
        ).collect(Collectors.toList());

        executorService.invokeAll(callableList).stream().forEach(future -> {
            try {
                // 会阻塞
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
