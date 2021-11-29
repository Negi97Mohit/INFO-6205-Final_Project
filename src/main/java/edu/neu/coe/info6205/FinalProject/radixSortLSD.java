package edu.neu.coe.info6205.FinalProject;

import java.io.FileNotFoundException;

public class radixSortLSD <X extends Comparable<X>>{
    public static void main(String[] args) throws FileNotFoundException {
        String resource="chinese_names.txt";
        String[] pin=toEng.generateList(resource);
        String[] chiToEng=new String[pin.length];

        for (int i = 0; i < pin.length; i++) { chiToEng[i] = regexMatch.getPingYin(pin[i]); }
        for (String word:chiToEng) System.out.println(word.length());
    }
    public static void sort(String[] a, int W)
    {
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];
        for (int d = W-1; d >= 0; d--)
        {
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }


}
