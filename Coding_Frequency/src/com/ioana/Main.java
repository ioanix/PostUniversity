package com.ioana;


import java.util.*;

public class Main {


    public static void main(String[] args) {

        boolean quit = false;
        printActions();


        while (!quit) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("\n Enter your action: (2 - to print the available actions)");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {

                case 0:
                    System.out.println("Program finished");
                    quit = true;
                    break;

                case 1:
                    System.out.println("Enter a string: ");
                    String str = scanner.nextLine();

                    HashMap<Character, Integer> returnedMap = countCharacterOccurence(str);
                    System.out.println("****************");


                    Map<Character, Integer> sortedMap = sortByValue(returnedMap);

                    System.out.println("**********");

                    Map<Character, Integer> codedMap = code(sortedMap);


                    System.out.println("Enter a string to code: ");

                    String str1 = scanner.nextLine().toLowerCase();
                    char[] charArray = str1.toCharArray();          // converting the input String to an array
                    String coded = str1;

                    for (char c : charArray) {

                        if (c != ' ') {

                            coded = coded.replaceAll(String.valueOf(c), String.valueOf(codedMap.get(c)));   // replace the character in the array with the corresponding value for the key in the map

                        }
                    }

                    System.out.println(coded);


                    Map<Integer, Character> decodedMap = decode(codedMap);

                    System.out.println("Enter a string to decode: ");
                    String str2 = scanner.nextLine();
                    char[] charArray1 = str2.toCharArray();

                    String number = "";
                    String decodedMessage = "";


                    for (int i = 0; i < charArray1.length; i++) {

                        if (str2.charAt(i) != ' ') {

                            number = number + str2.charAt(i);

                        } else {

                            decodedMessage = decodedMessage + decodedMap.get(Integer.parseInt(number)); // replace the number with the corresponding value for the key in the map
                            number = "";        // reseting the number
                        }

                    }

                    char lastChar = str2.charAt(str2.length() - 1);

                    decodedMessage = decodedMessage + decodedMap.get(Integer.parseInt(String.valueOf(lastChar)));

                    System.out.println(decodedMessage);
                    break;

                case 2:
                    printActions();
                    break;

            }

        }

    }


    public static HashMap<Character, Integer> countCharacterOccurence(String input) {

        HashMap<Character, Integer> charCount = new HashMap<>();        // creating a HashMap with the char as a key and the occurence as a value

        for (char c = 'a'; c <= 'z'; c++) {         // put all the letter in the map as keys and value 0

            charCount.put(c, 0);
        }

        char[] charArray = input.toCharArray();    // converting the input String to a char array


        for (char c : charArray) {

            if (c != ' ') {    // we don't want to count the spaces


                charCount.put(c, charCount.get(c) + 1);         // putting the char in the map and incrementing it's value by 1

            }

        }

        System.out.println(charCount);

//        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
//
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }


        return charCount;

    }


    public static Map<Character, Integer> sortByValue(Map<Character, Integer> myMap) {


        Map<Character, Integer> sortedMap = new LinkedHashMap<>();

        int max = -1;
        char maxKey = 0;

        while (sortedMap.size() != myMap.size()) {

            for (Map.Entry<Character, Integer> entry : myMap.entrySet()) {

                if (entry.getValue() > max && !sortedMap.containsKey(entry.getKey())) {

                    max = entry.getValue();     // searching for the max key
                    maxKey = entry.getKey();

                }

            }

            sortedMap.put(maxKey, max);

            max = -1;
        }

        System.out.println(sortedMap);

//        for(Map.Entry<Character, Integer> entry : sortedMap.entrySet()) {
//
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }

        return sortedMap;


//        List<Map.Entry<Character, Integer>> list = new ArrayList<>(myMap.entrySet());  // creating a list from the elements of the HashMap
//
//        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
//
//
//            @Override
//            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
//
//                return o2.getValue() - o1.getValue();
//            }
//        });

        // putting the sorted elements from the list back into the map
//
//        HashMap<Character, Integer> temp = new LinkedHashMap<>();
//
//        for (Map.Entry<Character, Integer> element : list) {
//
//            temp.put(element.getKey(), element.getValue());
//            System.out.println(element.getKey() + ":" + element.getValue());
//        }

        //System.out.println(temp);

//        for (Map.Entry<Character, Integer> entry : newMap.entrySet()) {
//
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//
//        return newMap;


    }


    public static Map<Character, Integer> code(Map<Character, Integer> myMap) {


        int count = 1;


        Iterator<Character> iterator = myMap.keySet().iterator();  // create an iterator for the keys in the map

        while (iterator.hasNext()) {

            Character ch = iterator.next();     //for the key with the max frequency -> change it's value to one, the next key -> change it's value to 2 etc.
            Integer value = count++;

            myMap.put(ch, value);

        }


        Iterator<Map.Entry<Character, Integer>> iterator1 = myMap.entrySet().iterator();

        while (iterator1.hasNext()) {

            Map.Entry<Character, Integer> entry = iterator1.next();

            myMap.put(entry.getKey(), entry.setValue(count++));

            //System.out.println(entry.getKey() + ":" + entry.getValue());


        }

        System.out.println(myMap);


        return myMap;

    }

    public static Map<Integer, Character> decode(Map<Character, Integer> myMap) {

        HashMap<Integer, Character> decodeMap = new HashMap<>();

        for (Map.Entry<Character, Integer> entry : myMap.entrySet()) {

            decodeMap.put(entry.getValue(), entry.getKey());        // creating a new map, from the coded map with keys as values and with values as keys
        }

        System.out.println(decodeMap);

//        for (Map.Entry<Integer, Character> entry : decodeMap.entrySet()) {
//
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }

        return decodeMap;

    }

    public static void printActions() {

        System.out.println("\nAvailable actions: press\n");
        System.out.println("0 - to quit the programm");
        System.out.println("1 - to code and decode a message: ");
        System.out.println("2 - print available actions: ");

    }

}


/// pentru mai multe informatii intreaba angajatii din restaurante
// drifturi in curtea scolii
// 17 18 1 4 10 1 14 14 1 18 1 17 1 6 5 12 1 14 1
























