package com.example.demo.java.utils.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample1 {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {

            // 任务完成，回调
            @Override
            public void run() {
                System.out.println("all of finished.");
            }
        });

        new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(20);
                System.out.println("T1 finished");
                cyclicBarrier.await();
                System.out.println("T1 The other thread finished too.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("T2 finished");
                cyclicBarrier.await();
                System.out.println("T2 The other thread finished too.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

//        cyclicBarrier.await();
//        System.out.println("all of finished.");
        while (true) {
            System.out.println(cyclicBarrier.getNumberWaiting());
            System.out.println(cyclicBarrier.getParties());
            System.out.println(cyclicBarrier.isBroken());
            TimeUnit.SECONDS.sleep(2);
        }

    }
}
