package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

public class L07_NonMainThreadSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .subscribe(new ResourceSubscriber<Long>() {

                    @Override
                    public void onNext(Long aLong) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("threadName : " + threadName + ", data: " + aLong);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("onComplete : " + threadName);
                    }
                });
        System.out.println("END");
        Thread.sleep(1000L);
    }
}
