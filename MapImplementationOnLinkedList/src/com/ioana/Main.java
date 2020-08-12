package com.ioana;

public class Main {

    public static void main(String[] args) {

        MyDictionary myDictionary = new MyDictionary();

        myDictionary.add(1, "mere");
        myDictionary.add(2, "pere");
        myDictionary.add(3, "mango");
        myDictionary.add(1, "melon");

        myDictionary.printMap();



    }


}
