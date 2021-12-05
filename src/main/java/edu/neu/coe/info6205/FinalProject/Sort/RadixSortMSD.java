package edu.neu.coe.info6205.FinalProject.Sort;


import edu.neu.coe.info6205.FinalProject.Utils.EnglishToChinese;
import edu.neu.coe.info6205.FinalProject.RegexMatch;
import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.ForkJoinPool;

import static edu.neu.coe.info6205.util.SortBenchmark.logger;

public class RadixSortMSD extends Sort {

    public static int threadCount=1;
    static ForkJoinPool threadFJP=new ForkJoinPool(threadCount);
    private static final int R      = 256;
    private static final int CUTOFF =  15;
    public static void main(String[] args) throws Exception {
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
        logger.info("Starting MSD Radix Sort");
        new RadixSortMSD().sort(chiToEng);
        EnglishToChinese eng=new EnglishToChinese();
        String[] res=eng.swapper(pin,beSort,chiToEng);
        System.out.println("Writing to file");
        BufferedWriter br = new BufferedWriter(new FileWriter("src/main/resources/Result.csv"));
        StringBuilder sb = new StringBuilder();
        for (String element : res) {
            sb.append(element);
            sb.append("\n");
        }
        br.write(sb.toString());
        br.close();

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

}