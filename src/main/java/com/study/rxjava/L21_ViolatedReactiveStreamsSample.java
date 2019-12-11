package com.study.rxjava;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L21_ViolatedReactiveStreamsSample {
    public static void main(String[] args) {
        Flowable.range(1,3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe : start");
                        s.request(Long.MAX_VALUE);
                        System.out.println("onSubscribe : end");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError"+t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}
