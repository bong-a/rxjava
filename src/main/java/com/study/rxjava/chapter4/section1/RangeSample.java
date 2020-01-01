package com.study.rxjava.chapter4.section1;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

public class RangeSample {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.range(10,3);
        flowable.subscribe(new DebugSubscriber<>());
    }
}
