package edu.neu.coe.info6205.FinalProject;

import edu.neu.coe.info6205.sort.linearithmic.Partition;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort;


import java.io.IOException;
import java.util.ArrayList;

public class DualPivot<X extends Comparable<X>>   {
    public static void main(String[] args) throws Exception {

//        String resource = "chinese_names.txt";
//        String[] pin = toEng.generateList(resource);
//        System.out.println("PIN");
//        System.out.println(pin);
//        String[] chiToEng = new String[pin.length];
//
//        for (int i = 0; i < pin.length; i++) {
//            chiToEng[i] = regexMatch.getPingYin(pin[i]);
//        }
        int count = 0;
//        for (String da : chiToEng) {
//            System.out.println(da + pin[count]);
//            count++;
//        }
        System.out.println(count);
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(3);
        arr.add(43);
        arr.add(20);
        arr.add(35);
        arr.add(111);
        arr.add(99);
        arr.add(5);
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }

//        Partition<X> send = new Partition<X>(arr, 0, arr.size());


    }

}
