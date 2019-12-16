package com.study.rxjava.chapter2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class L12_AtomicCounterSample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final AtomicCounter counter = new AtomicCounter();
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();

        // new thread
        Future<Boolean> future1 = executorService.submit(task, true);

        // new thread
        Future<Boolean> future2 = executorService.submit(task, true);

        if (future1.get() && future2.get()) {
            System.out.println(counter.get());
        } else {
            System.err.println("FAIL");
        }
        executorService.shutdown();
    }
}
