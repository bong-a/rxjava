package com.study.rxjava.chapter4.section1;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

import java.time.LocalTime;

public class DeferSample<T> {

    public static void main(String[] args) throws Exception {

        Flowable<LocalTime> flowable =
                Flowable.defer(() -> Flowable.just(LocalTime.now()));

        // 구독한다
        flowable.subscribe(new DebugSubscriber<>("No. 1"));

        // 잠시 기다린다
        Thread.sleep(2000L);

        // 다시 구독한다
        flowable.subscribe(new DebugSubscriber<>("No. 2"));
    }

}