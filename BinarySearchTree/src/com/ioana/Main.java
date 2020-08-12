package com.ioana;

public class Main {

    public static void main(String[] args) {

        BinarySearchTree bst = new BinarySearchTree();

        bst.add(15);
        bst.add(7);
        bst.add(30);
        bst.add(1);
        bst.add(9);
        bst.add(20);
        bst.add(35);

        bst.searchByValue(1);
        bst.searchByValue(56);

        if(bst.searchByValue(1)) {

            System.out.println("found");
        }

        else {

            System.out.println("not found");
        }


    }
}
