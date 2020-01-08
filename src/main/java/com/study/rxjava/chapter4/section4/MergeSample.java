package com.study.rxjava.chapter4.section4;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * 예제 4-75 merge(source1, source2) 예제
 */
public class MergeSample {

    public static void main(String[] args) throws Exception {
        // 병합(merge) 하는 대상
        Flowable<Long> flowable1 =
                // 300밀리초마다 데이터를 통지한다
                Flowable.interval(200L, TimeUnit.MILLISECONDS)
                        // 5건까지 통지한다
                        .take(20);

        // 병합하는 대상
        Flowable<Long> flowable2 =
                // 500밀리초마다 데이터를 통지한다
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 2건까지 통지한다
                        .take(5)
                        // 100을 더한다
                        .map(data -> data + 100L);

        // 여러 Flowable을 병합한다
        Flowable<Long> result = Flowable.merge(flowable1, flowable2);
//                .mergeWith(Flowable.interval(250L,TimeUnit.MILLISECONDS).take(5));

        // 구독한다
        result.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다
        Thread.sleep(2000L);
    }

    private static Observable<String> getSource1() {
        final List<String> items1 = Arrays.asList("src1_one", "src1_two", "src1_three", "src1_four");
        return Observable.fromIterable(items1);
    }

}
