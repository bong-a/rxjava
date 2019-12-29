package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

public class L06_MainThreadSample {
    public static void main(String[] args) {
        System.out.println("START");
        Flowable.just(1, 2, 3)
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("threadName : " + threadName+", data: "+integer);
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
    }
}
