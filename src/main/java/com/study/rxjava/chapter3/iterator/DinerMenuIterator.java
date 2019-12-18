package com.study.rxjava.chapter3.iterator;

import java.util.Iterator;

public class DinerMenuIterator implements Iterator<MenuItem> {
    MenuItem[] list;
    int position = 0;

    public DinerMenuIterator(MenuItem[] list) {
        this.list = list;
    }


    @Override
    public MenuItem next() {
        MenuItem menuItem = list[position];
        position += 1;
        return menuItem;
    }

    @Override
    public boolean hasNext() {
        if (position >= list.length || list[position] == null) return false;
        else return true;
    }

    @Override
    public void remove() { // 반드시 기능을 제공하지 않아도됨 그렇다면 java.lang.UnsupportedOperationException을 던지도록 하면됨
        if (position <= 0) throw new IllegalStateException("next()가 한번도 호출되지 않음.");
        if (list[position - 1] != null) {
            for (int i = position - 1; i < (list.length - 1); i++) {
                list[i] = list[i + 1];
            }
            list[list.length - 1] = null;
        }
    }

}
