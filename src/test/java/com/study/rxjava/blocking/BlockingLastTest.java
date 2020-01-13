package com.study.rxjava.blocking;

import io.reactivex.Flowable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** 예제 6-14 blockingLast() 테스트  */
public class BlockingLastTest {
  
  @Test
  public void 마지막통지데이터얻기() {
    // 받아온 결과
    long actual =
        // Flowable을 생성한다
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            // 3건까지 통지한다
            .take(3)
            // 마지막 통지 데이터를 메인 스레드에서 얻는다
            .blockingLast();
    
    // 확인한다
    assertThat(actual, is(2L));
  }
}
