package com.study.rxjava.chapter3.observer.badExample;

public class Client {
    public static void main(String[] args) {
        ScoreRecord scoreRecord = new ScoreRecord();

        DataSheetView dataSheetView=new DataSheetView(scoreRecord,3);
        scoreRecord.setDataSheetView(dataSheetView);

        for (int index = 1;index<=5;index++){
            int score = index * 10;
            System.out.println("Adding "+score);
            scoreRecord.addScore(score);
        }
    }
}
