package edu.neu.coe.info6205.FinalProject;


import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.SortBenchmark;

import java.io.IOException;

public class radixSort extends Sort{


    private static final int R      = 256;
    private static final int CUTOFF =  15;
    public static void main(String[] args) throws IOException {
        String resource="chinese_names.txt";
        String[] pin=toEng.generateList(resource);
        System.out.println("PIN");
        System.out.println(pin);
        String[] chiToEng=new String[pin.length];

        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }
        int n = chiToEng.length;
        new radixSort().sort(chiToEng);
        System.out.println("\n");
        radixBench(chiToEng);

    }

    static void print(String[] str, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(str[i] + " ");
        }
        System.out.println();
    }

    public void sort(String[] a) {
        int N = a.length;
        String[] aux = new String[N];
        partition(a, 0, N-1, 0, aux);
    }

    private static int charAt(String s, int d) {
        if (d == s.length())
            return -1;
        return s.charAt(d);
    }

    private static void partition(String[] a, int lo, int hi, int d, String[] aux) {

        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c+2]++;
        }

        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c+1]++] = a[i];
        }

        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];


        for (int r = 0; r < R; r++)
            partition(a, lo + count[r], lo + count[r+1] - 1, d+1, aux);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    public static void radixBench(String[] arr) throws IOException {
        Config config = Config.load(SortBenchmark.class);
        SortBenchmark sb=new SortBenchmark(config);
        sb.benchmarkStringSorters(arr,5000,5000);


    }

}