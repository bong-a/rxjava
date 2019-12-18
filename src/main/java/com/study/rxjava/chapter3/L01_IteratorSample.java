package com.study.rxjava.chapter3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class L01_IteratorSample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c");
        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()){
            String value = iterator.next();
            System.out.println(value);
        }
    }
}
