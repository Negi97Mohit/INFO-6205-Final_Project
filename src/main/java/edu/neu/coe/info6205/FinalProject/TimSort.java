package edu.neu.coe.info6205.FinalProject;

import java.io.IOException;

public class TimSort {

    public static void main(String[] args) throws IOException {
        String resource="chinese_names.txt";
        String[] pin=toEng.generateList(resource);
        String[] chiToEng=new String[pin.length];

        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }

        int count =0 ;
        for(String ss: chiToEng){
            System.out.println(ss + pin[count]);
            count++;
        }
        System.out.println(count);

    }
}
