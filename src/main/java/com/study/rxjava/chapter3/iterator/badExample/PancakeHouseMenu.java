package com.study.rxjava.chapter3.iterator.badExample;

import com.study.rxjava.chapter3.iterator.MenuItem;

import java.util.ArrayList;

public class PancakeHouseMenu {
    ArrayList<MenuItem> menuItems;

    public PancakeHouseMenu() {
        this.menuItems = new ArrayList<>();
        addItem("K&B 팬케이크 세트", "스크램블드 에그와 토스트가 곁들여진 펜케이크", true, 2.99);
        addItem("레귤러 팬케이크 세트", "달걀 후라이와 소시지가 곁들여진 펜케이크", false, 2.99);
        addItem("블루베리 펜케이크", "신선한 블루베리와 블루베리 시럽으로 만든 펜케이크", true, 3.49);
        addItem("와플", "와플, 취향에 따라 블루베리나 딸기를 얹을 수 있습니다.", true, 3.59);

    }

    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

}
