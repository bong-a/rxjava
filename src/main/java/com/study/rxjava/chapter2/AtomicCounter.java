package com.study.rxjava.chapter2;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    void increment(){
       count.incrementAndGet();
    }

    int get(){
        return count.get();
    }
}
