package com.study.rxjava.chapter3;

import io.reactivex.Flowable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class L12_ConcatMapEagerSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .concatMapEager(data -> {
                    System.out.println("flatMap : " + data);
                    return Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS);
                });
        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(threadName + " data : " + data + ", time :" + time);
        });
        Thread.sleep(2000L);
    }
}
