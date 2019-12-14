package com.study.rxjava.chapter1;

import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Date;

public class L11_FlowableSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                String[] datas ={"Hello, World!","안녕, RxJava"};
                //구독이  해지되면 처리를 중단한다.
                for (String data:datas){
                    if(emitter.isCancelled()){
                        return;
                    }
                    emitter.onNext(data);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER); //초과한 데이터는 버퍼링 한다.
        flowable
                //Subscriber 처리를 개별 스레드에서 처리한다.
                .observeOn(Schedulers.computation())
                //구독한다.
                .subscribe(new Subscriber<String>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        //Subscription을 Subscriber에 보관한다.
                        this.subscription=subscription;
                        //받을 데이터 개수 요청
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(new Date()+":"+threadName+" : "+s);
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName+" : 완료");
                    }
                });
        Thread.sleep(500L);
    }
}
