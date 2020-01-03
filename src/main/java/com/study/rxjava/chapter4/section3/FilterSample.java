package com.study.rxjava.chapter4.section3;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/** 예제 4-47 filter(predicate) 예제 */
public class FilterSample {
  
  public static void main(String[] args) throws Exception {
    Flowable<Long> flowable =
        // Flowable을 생성한다
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            // 짝수만 통지한다
            .filter(data -> data % 2 == 0);
    
    // 구독한다
    flowable.subscribe(new DebugSubscriber<>());
    
    // 잠시 기다린다
    Thread.sleep(3000L);
  }
}
