package com.study.rxjava.chapter3;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L16_RetrySample {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.<Integer> create(emitter -> {
            System.out.println("Start Flowable Process");
            for (int i = 1 ;i<=3;i++){
                if(i==2){
                    throw new Exception("Exception");
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
            System.out.println("End Flowable Process");
        }, BackpressureStrategy.BUFFER)
                .doOnSubscribe(subscription -> {
                    System.out.println("flowable : doOnSubscribe");
                })
                .retry(2);

        flowable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("subscriber : onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("data = "+integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error ="+t);
            }

            @Override
            public void onComplete() {
                System.out.println("end");
            }
        });
    }
}
