package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class L03_FlowableSample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        Flowable<String> flowable = Flowable.fromIterable(list);
        flowable.subscribe(System.out::println);
    }
}
