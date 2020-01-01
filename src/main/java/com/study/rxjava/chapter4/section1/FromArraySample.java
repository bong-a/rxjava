package com.study.rxjava.chapter4.section1;

import com.google.common.collect.Lists;
import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.Arrays;

public class FromArraySample {
    public static void main(String[] args) {
        String[] strings = new String[3];
        strings[0]="Hello";
        strings[1]="Happy";
        strings[2]="NewYear";

//        Flowable<String> flowable = Flowable.fromArray("A","B","C","D","E");
        Flowable<String> flowable = Flowable.fromArray(strings);
        flowable.subscribe(new DebugSubscriber<>());
        Flowable<String> flowable1 = Flowable.fromIterable(Arrays.asList("Happy","NewYear","2020"));
        flowable1.subscribe(new DebugSubscriber<>());
    }
}
