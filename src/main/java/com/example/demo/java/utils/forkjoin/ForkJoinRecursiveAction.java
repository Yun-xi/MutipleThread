package com.example.demo.java.utils.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoinRecursiveAction {

    private final static int MAX_THRESHOLD = 3;

    private final static AtomicInteger SUM = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        forkJoinPool.submit(new CalculatedRecursiveTask(0, 10));

        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println(SUM);
    }

    private static class CalculatedRecursiveTask extends RecursiveAction {

        private final int start;

        private final int end;

        CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                // 如果任务大于阈值，就分裂成两个子任务计算
                int middle = (start + end) / 2;
                CalculatedRecursiveTask leftTask = new CalculatedRecursiveTask(start, middle);
                CalculatedRecursiveTask rightTask = new CalculatedRecursiveTask(middle + 1, end);

                // 执行子任务
                leftTask.fork();
                rightTask.fork();
            }
        }
    }

}
