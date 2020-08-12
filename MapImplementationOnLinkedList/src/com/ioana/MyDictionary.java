package com.ioana;


public class MyDictionary {

    MapNode first;

    public MyDictionary() {


    }

    public void add(int newKey, String newValue) {

        MapNode newNode = new MapNode(newKey, newValue);
        boolean found = false;

        if (first == null) {             //daca lista e vida noul nod e primul

            first = newNode;
        } else {

            MapNode current = first;

            while (current != null && !found) {

                if (current.key == newKey) {     // daca lista nu e vida, dar mai exista cheia, inlocuim valoarea

                    current.value = newValue;
                    found = true;

                }

                current = current.next;

            }

            if (!found) {

                newNode.next = first;       // daca nu mai exista cheia, adaugam perechea cheie - valoare
                first = newNode;

            }
        }
    }

    public void printMap() {

        MapNode current = first;

        while (current != null) {

            System.out.println(current.key + ":" + current.value);
            current = current.next;

        }

    }
}
