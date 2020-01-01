package com.study.rxjava.chapter4.section1;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

public class FromCallableSample {
    public static void main(String[] args) {
        Flowable<Long> flowable = Flowable.fromCallable(System::currentTimeMillis);
        flowable.subscribe(new DebugSubscriber<>());
    }
}
