package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.RegexMatch;
import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;

import java.io.FileNotFoundException;
import java.util.concurrent.ForkJoinPool;

public class RadixSortLSD<X extends Comparable<X>> extends Sort {
    public static int threadCount=1;
    static ForkJoinPool threadFJP=new ForkJoinPool(threadCount);
    private static final int BITS_PER_BYTE = 8;
    private static int w;

    public static void main(String[] args) throws FileNotFoundException {
        String resource = "chinese_names.txt";
        String[] pin = ChineseToEnglish.generateList(resource);
        String[] chiToEng = new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = RegexMatch.getPingYin(pin[i]);
        }

        int min = 10;
        for (String word : chiToEng) {
            if (word.length() < min)
                min = word.length();
        }
        int n = chiToEng.length;
        w = min;
        for (int i = 0; i < n; i++)
            assert chiToEng[i].length() == w : "String same length";

        new RadixSortLSD().sort(chiToEng);

    }

    public void sort(String[] a) {
        int min = 10;
        for (String word : a) {
            if (word.length() < min)
                min = word.length();
        }
        int n = a.length;
        w = min;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[n];

        for (int d = w - 1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R + 1];
            for (int i = 0; i < n; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // move data
            for (int i = 0; i < n; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // copy back
            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }
}
