package com.study.rxjava.chapter2;

public class Counter {
    private volatile int count;

    void increment(){
        //하나의 작업처럼 보이지만 실제로는 여러 작업으로 이루어짐(읽기 고치기 쓰기)
        count++;
    }

    int get(){
        return count;
    }
}
