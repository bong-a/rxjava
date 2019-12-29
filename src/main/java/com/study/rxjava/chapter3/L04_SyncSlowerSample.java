package com.study.rxjava.chapter3;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class L04_SyncSlowerSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("emit :\t"+System.currentTimeMillis()+"  millis: "+data
                ))
                .subscribe(data->{Thread.sleep(2000L);
                    System.out.println("subscribe :\t"+System.currentTimeMillis()+" millis :"+data);});

        Thread.sleep(5000L);
    }
}
