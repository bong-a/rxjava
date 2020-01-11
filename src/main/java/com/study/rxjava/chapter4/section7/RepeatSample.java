package com.study.rxjava.chapter4.section7;

import com.study.rxjava.chapter4.DebugSubscriber;
import io.reactivex.Flowable;

/** 예제 4-95 repeat(long)의 예제 */
public class RepeatSample {
  
  public static void main(String[] args) {
    Flowable<String> flowable =
        // Flowable을 생성한다
        Flowable.just("A", "B", "C")
            // 통지를 반복한다
            .repeat(2);
    
    // 구독한다
    flowable.subscribe(new DebugSubscriber<>());
  }
}
