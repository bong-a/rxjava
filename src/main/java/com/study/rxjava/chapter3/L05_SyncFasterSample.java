package com.study.rxjava.chapter3;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class L05_SyncFasterSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("emit : "+System.currentTimeMillis()+"  millis: "+data
                ))
                .subscribe(data->{
                    Thread.sleep(500L);
                    System.out.println("subscribe :\t"+System.currentTimeMillis()+" millis :"+data);
                });

        Thread.sleep(5000L);
    }
}
