package com.study.rxjava.chapter4;

import io.reactivex.subscribers.DisposableSubscriber;

public class DebugSubscriber<T> extends DisposableSubscriber<T> {
    private String label;

    public DebugSubscriber() {
        super();
    }

    public DebugSubscriber(String label) {
        super();
        this.label = label;
    }

    @Override
    public void onNext(T t) {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + " : " + t);
        } else {
            System.out.println(threadName + " : " + label + " : " + t);
        }
    }

    @Override
    public void onError(Throwable t) {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + " : " + t);
        } else {
            System.out.println(threadName + " : " + label + ": error : " + t);
        }
    }

    @Override
    public void onComplete() {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + " : complete");
        } else {
            System.out.println(threadName + " : " + label + " : complete");
        }
    }
}
