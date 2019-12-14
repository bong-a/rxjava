package com.study.rxjava.chapter1;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Date;

public class L18_ObservableSample {

    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String[] datas={"Hello World!","Hello RxJava!"};

                for (String data : datas) {
                    if(emitter.isDisposed()){
                        return;
                    }
                    System.out.println(System.currentTimeMillis()+" : "+data);
                    emitter.onNext(data);
                }
                emitter.onComplete();
            }
        });
        observable
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //nothing
                    }

                    @Override
                    public void onNext(String s) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(System.currentTimeMillis()+" : "+threadName+" : "+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName+" : onComplete");
                    }
                });
        Thread.sleep(500L);
    }

}
