package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

public class L09_ObserveOnSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(aLong -> System.out.println("doOnNext : "+aLong))
                .onBackpressureDrop();
        flowable.observeOn(Schedulers.computation(),false,2)
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        try{
                            Thread.sleep(1000L);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                            System.exit(1);
                        }
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName+" : "+aLong);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Thread.sleep(7000L);
    }
}
