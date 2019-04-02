package com.example.demo.java.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 等待
 */
public class PhaserExample4 {

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        /*final Phaser phaser = new Phaser();

        new Thread(phaser::arrive).start();

        TimeUnit.SECONDS.sleep(4);*/

        final Phaser phaser = new Phaser(4);
        for (int i = 0; i < 4; i++) {
            new ArriveTask(phaser, i).start();
        }

        phaser.awaitAdvance(phaser.getPhase());

        System.out.println("The phaser one worker finisher");
    }

    private static class ArriveTask extends Thread {

        private final Phaser phaser;

        private ArriveTask(Phaser phaser, int no) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + " start working.");
            sleepSeconds();
            System.out.println(getName() + " The phaser one is running.");
            int arrive = phaser.arrive();

            sleepSeconds();

            System.out.println(getName() + " keep to do other thing.");
        }
    }

    private static void sleepSeconds() {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
