package com.study.rxjava.chapter4.section1;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

public class EmptySample<T> {

    public static void main(String[] args) {
        Flowable
                // 빈 Flowable을 생성한다
                .empty()
                // 구독한다
                .subscribe(new DebugSubscriber<>());
    }

}
