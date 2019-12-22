package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class L08_SubscribeOnSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just(1,2,3,4,5)
//                .interval(100L, TimeUnit.MILLISECONDS,Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.single())
                .subscribe(data->{
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName+" : "+data);
                });

        Thread.sleep(500L);
    }
}
