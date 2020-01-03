package com.study.rxjava.chapter4.section3;

import java.util.concurrent.TimeUnit;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

/** 예제 4-55 take(count) 예제  */
public class TakeSample {
  
  public static void main(String[] args) throws Exception {
    Flowable<Long> flowable =
        // Flowable을 생성한다
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            // 3건까지 통지한다
//            .take(3);
    .take(1,TimeUnit.MILLISECONDS);
    // 구독한다
    flowable.subscribe(new DebugSubscriber<>());
    
    // 잠시 기다린다
    Thread.sleep(4000L);
  }
  
}
