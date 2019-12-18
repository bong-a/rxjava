package com.study.rxjava.chapter3.observer;

import java.util.ArrayList;
import java.util.List;

/* 추상화된 변경 관심 대상 데이터 */
// 즉, 데이터에 공통적으로 들어가야하는 메서드들 -> 일반화
public abstract class Subject {
    // 추상화된 통보 대상 목록 (즉, 출력 형태에 대한 Observer)
    private List<Observer> observers = new ArrayList<Observer>();

    // 통보 대상(Observer) 추가
    public void attach(Observer observer) { observers.add(observer);}
    // 통보 대상(Observer) 제거
    public void detach(Observer observer) { observers.remove(observer);}
    // 각 통보 대상(Observer)에 변경을 통보. (List<Observer>객체들의 update를 호출)
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
