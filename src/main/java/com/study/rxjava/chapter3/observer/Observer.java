package com.study.rxjava.chapter3.observer;

/* 추상화된 통보 대상 */
public interface Observer {
    // 데이터 변경을 통보했을 때 처리하는 메서드
    public abstract void update();
}

