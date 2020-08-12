package com.ioana;

public class BinarySearchTree {

    NodeBST root;

    public BinarySearchTree() {

        root = null;
    }

    public void add(int newValue) {

        NodeBST newNode = new NodeBST(newValue);

        NodeBST anterior = null;

        if(root == null) {

            root = newNode;

        }

        else {

            NodeBST current = root;

            while (current != null) {

                anterior = current;

                if (current.value == newValue) {

                    return;

                } else if (current.value > newValue) {

                    current = current.left;

                } else {

                    current = current.right;
                }

                if (anterior.value > newValue) {

                    anterior.left = newNode;

                } else {

                    anterior.right = newNode;
                }

            }
        }

    }


    public boolean searchByValue(int newValue) {

        if (root == null) {

            return false;

        } else {

            NodeBST current = root;

            while (current != null) {

                if (current.value == newValue) {

                    return true;

                } else if (current.value > newValue) {

                    current = current.left;

                } else {

                    current = current.right;
                }

            }

            return false;

        }

    }



}























