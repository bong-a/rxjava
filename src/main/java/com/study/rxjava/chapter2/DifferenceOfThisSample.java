package com.study.rxjava.chapter2;

import io.reactivex.functions.Action;

public class DifferenceOfThisSample {

    public static void main(String[] args) throws Exception {
        DifferenceOfThisSample target = new DifferenceOfThisSample();
        target.execute();
    }

    public void execute() throws Exception{
        Action anonymous = new Action(){

            @Override
            public void run() {
                System.out.println("anonymous class's this : "+this);
            }
        };

        Action lamda = () -> System.out.println("lamda's this : "+ this);

        anonymous.run();
        lamda.run();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
