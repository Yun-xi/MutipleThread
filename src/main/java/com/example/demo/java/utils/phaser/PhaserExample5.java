package com.example.demo.java.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 打断的等待
 */
public class PhaserExample5 {

    public static void main(String[] args) throws InterruptedException {

        final Phaser phaser = new Phaser(3);
        Thread thread = new Thread(() -> {
            try {
                // 如果里面的phase不是当前的phase则立即返回
                // phaser.awaitAdvanceInterruptibly(phaser.getPhase());
                phaser.awaitAdvanceInterruptibly(phaser.getPhase(), 10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(20);
        thread.interrupt();
    }
}
