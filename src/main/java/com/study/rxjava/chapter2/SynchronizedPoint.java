package com.study.rxjava.chapter2;

public class SynchronizedPoint {
    private int x;
    private int y;

    private final Object lock = new Object();

    void rightUp() {
        synchronized (lock) {
            x++;
            y++;
        }
    }

    int getX() {
        synchronized (lock) {
            return x;
        }
    }

    int getY() {
        synchronized (lock) {
            return y;
        }
    }
}
