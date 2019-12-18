package com.study.rxjava.chapter3.observer;

public class Client {
    public static void main(String[] args) {
        ScoreRecord scoreRecord = new ScoreRecord();

        // 3개까지의 점수만 출력함
        DataSheetView dataSheetView = new DataSheetView(scoreRecord, 3);
        // 최대값, 최소값만 출력함
        MinMaxView minMaxView = new MinMaxView(scoreRecord);

        // 각 통보 대상 클래스를 Observer로 추가
        scoreRecord.attach(dataSheetView);
        scoreRecord.attach(minMaxView);

        // 10 20 30 40 50을 추가
        for (int index = 1; index <= 5; index++) {
            int score = index * 10;
            System.out.println("Adding " + score);
            // 추가할 때마다 최대 3개의 점수 목록과 최대/최소값이 출력됨
            scoreRecord.addScore(score);
            System.out.println();
        }
    }
}
