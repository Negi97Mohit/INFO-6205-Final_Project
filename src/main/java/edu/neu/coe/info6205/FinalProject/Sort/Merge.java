package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;
import edu.neu.coe.info6205.FinalProject.RegexMatch;
import edu.neu.coe.info6205.FinalProject.Utils.EnglishToChinese;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Merge {
    public static void main(String[] args) throws IOException {
        String resource="chinese_names.txt";
        String[] pin= ChineseToEnglish.generateList(resource);
        String[] chiToEng=new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = RegexMatch.getPingYin(pin[i]);
        }
        int n = chiToEng.length;
        String[] beSort=new String[chiToEng.length];
        for(int i=0;i<n;i++)
            beSort[i]=chiToEng[i];
        String[] ClassOne = Arrays.copyOfRange(chiToEng, 0, chiToEng.length/2);
        String[] ClassTwo = Arrays.copyOfRange(chiToEng, chiToEng.length/2, chiToEng.length);
        String[] names = new String[ClassOne.length + ClassTwo.length];
        mergeSort(ClassOne);
        mergeSort(ClassTwo);
        merge(names, ClassOne, ClassTwo);

        mergeSort(names);
        EnglishToChinese eng=new EnglishToChinese();
        String[] res=eng.swapper(pin,beSort,names);
        System.out.println("Writing to file");
        BufferedWriter br = new BufferedWriter(new FileWriter("src/main/resources/MergeSortResult.csv"));
        StringBuilder sb = new StringBuilder();
        for (String element : res) {
            sb.append(element);
            sb.append("\n");
        }
        br.write(sb.toString());
        br.close();

    }

    public static void mergeSort(String[] names) {
        if (names.length >= 2) {
            String[] left = new String[names.length / 2];
            String[] right = new String[names.length - names.length / 2];

            for (int i = 0; i < left.length; i++) {
                left[i] = names[i];
            }

            for (int i = 0; i < right.length; i++) {
                right[i] = names[i + names.length / 2];
            }

            mergeSort(left);
            mergeSort(right);
            merge(names, left, right);
        }
    }

    public static void merge(String[] names, String[] left, String[] right) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < names.length; i++) {
            if (b >= right.length || (a < left.length && left[a].compareToIgnoreCase(right[b]) < 0)) {
                names[i] = left[a];
                a++;
            } else {
                names[i] = right[b];
                b++;
            }
        }
    }
}
