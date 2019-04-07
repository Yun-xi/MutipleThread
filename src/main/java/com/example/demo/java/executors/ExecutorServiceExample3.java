package com.example.demo.java.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ExecutorServiceExample3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // testInvokeAny();
        // testInvokeAnyTimeOut();
        // testInvokeAll();
        // testInvokeAllTimeOut();
        // testSubmitRunnable();
        testSubmitRunnableWithResult();
    }

    private static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<Integer>> callableList = IntStream.range(0, 5).boxed().map(
                i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    return i;
                }
        ).collect(toList());
        Integer value = executorService.invokeAny(callableList);
        System.out.println("================finished================");
        System.out.println(value);

        /**
         * pool-1-thread-3 : 2
         * ================finished================
         * 2
         *
         * invokeAny()会阻塞，返回一个值之后，其他线程继续执行
         */
    }

    private static void testInvokeAnyTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Integer value = executorService.invokeAny(
                IntStream.range(0, 5).boxed().map(
                        i -> (Callable<Integer>) () -> {
                            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                            System.out.println(Thread.currentThread().getName() + " : " + i);
                            return i;
                        }
                ).collect(toList()), 1, TimeUnit.SECONDS
        );
        System.out.println("================finished================");
        System.out.println(value);

        /**
         * pool-1-thread-2 : 1
         * Exception in thread "main" java.util.concurrent.TimeoutException
         * 	at java.util.concurrent.AbstractExecutorService.doInvokeAny(AbstractExecutorService.java:184)
         * 	at java.util.concurrent.AbstractExecutorService.invokeAny(AbstractExecutorService.java:225)
         * 	at com.example.demo.java.executors.ExecutorServiceExample3.testInvokeAnyTimeOut(ExecutorServiceExample3.java:41)
         * 	at com.example.demo.java.executors.ExecutorServiceExample3.main(ExecutorServiceExample3.java:14)
         */
    }

    private static void testInvokeAll() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.invokeAll(
                IntStream.range(0, 5).boxed().map(
                        i -> (Callable<Integer>) () -> {
                            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                            System.out.println(Thread.currentThread().getName() + " : " + i);
                            return i;
                        }
                ).collect(toList())
        ).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);

        /**
         * pool-1-thread-4 : 3
         * pool-1-thread-1 : 0
         * pool-1-thread-3 : 2
         * pool-1-thread-5 : 4
         * pool-1-thread-2 : 1
         * 0
         * 1
         * 2
         * 3
         * 4
         *
         * 等全部task执行完成，才进行返回
         */
    }

    private static void testInvokeAllTimeOut() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.invokeAll(
                IntStream.range(0, 5).boxed().map(
                        i -> (Callable<Integer>) () -> {
                            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                            System.out.println(Thread.currentThread().getName() + " : " + i);
                            return i;
                        }
                ).collect(toList()), 1, TimeUnit.SECONDS
        ).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);

        /**
         * pool-1-thread-1 : 0
         * 0
         * Exception in thread "main" java.lang.RuntimeException: java.util.concurrent.CancellationException
         * 	at com.example.demo.java.executors.ExecutorServiceExample3.lambda$testInvokeAllTimeOut$9(ExecutorServiceExample3.java:118)
         * 	at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)
         * 	at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1374)
         * 	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:481)
         * 	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
         * 	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
         * 	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
         * 	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
         * 	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
         * 	at com.example.demo.java.executors.ExecutorServiceExample3.testInvokeAllTimeOut(ExecutorServiceExample3.java:120)
         * 	at com.example.demo.java.executors.ExecutorServiceExample3.main(ExecutorServiceExample3.java:15)
         * Caused by: java.util.concurrent.CancellationException
         * 	at java.util.concurrent.FutureTask.report(FutureTask.java:121)
         * 	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
         * 	at com.example.demo.java.executors.ExecutorServiceExample3.lambda$testInvokeAllTimeOut$9(ExecutorServiceExample3.java:116)
         * 	... 10 more
         */
    }

    private static void testSubmitRunnable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Object NULL = future.get();
        System.out.println("R: " + NULL);

        /**
         * R: null
         */
    }

    private static void testSubmitRunnableWithResult() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String result = "DONE";
        Future<String> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);
        System.out.println(future.get());

        /**
         * DONE
         */
    }
}
