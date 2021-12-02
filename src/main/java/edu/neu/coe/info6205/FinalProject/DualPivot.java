package edu.neu.coe.info6205.FinalProject;

import java.io.IOException;

public class DualPivot {
    public static void main(String[] args) throws Exception{

            String resource="chinese_names.txt";
            String[] pin=toEng.generateList(resource);
            System.out.println("PIN");
            System.out.println(pin);
            String[] chiToEng=new String[pin.length];

            for (int i = 0; i < pin.length; i++) {
                chiToEng[i] = regexMatch.getPingYin(pin[i]);
            }
            int count = 0;
            for (String da : chiToEng){
                System.out.println(da + pin[count]);
                count++;
            }
            System.out.println(count);

    }
}
