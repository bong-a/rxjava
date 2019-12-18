package com.study.rxjava.chapter3.observer;

import java.util.ArrayList;
import java.util.List;

/* 구체적인 변경 감시 대상 데이터 */
// 출력형태 2개를 가질 때
public class ScoreRecord extends Subject{
    private List<Integer> scores = new ArrayList<Integer>(); // 점수를 저장함
    // 새로운 점수를 추가 (상태 변경)
    public void addScore(int score) {
        scores.add(score); // scores 목록에 주어진 점수를 추가함
        notifyObservers(); // scores가 변경됨을 각 통보 대상(Observer)에게 통보함
    }
    public List<Integer> getScoreRecord() { return scores; }
}
