package com.study.rxjava.chapter3.observer.badExample;

import java.util.ArrayList;
import java.util.List;

public class ScoreRecord {
    private List<Integer> scores = new ArrayList<>();
    private DataSheetView dataSheetView;

    public void setDataSheetView(DataSheetView dataSheetView){
        this.dataSheetView = dataSheetView;
    }

    public void addScore(int score){
        scores.add(score); // 점수 목록에 주어진 점수 추가
        dataSheetView.update(); // 점수가 변경됨을 통보
    }
    // 출력하는 부분에서 변화된 내용을 얻어감
    public List<Integer> getScoreRecord() { return scores; }
}
