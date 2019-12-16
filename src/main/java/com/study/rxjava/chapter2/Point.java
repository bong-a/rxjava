package com.study.rxjava.chapter2;

import java.util.concurrent.atomic.AtomicInteger;

public class Point {
    private final AtomicInteger x = new AtomicInteger(0);
    private final AtomicInteger y = new AtomicInteger(0);

    void increment() {
        x.incrementAndGet();
        y.incrementAndGet();
    }

    int getX() {
        return x.get();
    }

    int getY() {
        return y.get();
    }
}
