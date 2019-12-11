package com.study.rxjava;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class L17_SubscriptionCancelSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(200L, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {
                    private Subscription subscription;
                    private long startTime;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.startTime = System.currentTimeMillis();
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if ((System.currentTimeMillis() - startTime) > 500) {
                            subscription.cancel();
                            System.out.println("cancel subscription");
                            return;
                        }
                        System.out.println("data : " + aLong);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Thread.sleep(2000L);
    }
}
