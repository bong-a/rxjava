package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class L13_CounterSample {
    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        Flowable.range(1,10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(data ->counter.increment(),
                        error -> System.out.println("error"+error),
                        ()-> System.out.println("onComplete : counter.get() : "+counter.get()));
        Flowable.range(1,10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(data ->counter.increment(),
                        error -> System.out.println("error"+error),
                        ()-> System.out.println("onComplete : counter.get() : "+counter.get()));

        Thread.sleep(2000L);
    }
    static class Counter{
        private volatile int count;
        void increment(){
            count++;
        }
        int get(){
            return count;
        }
    }
}
