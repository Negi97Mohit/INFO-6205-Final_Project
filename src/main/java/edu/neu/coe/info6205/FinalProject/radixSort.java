package edu.neu.coe.info6205.FinalProject;


public class radixSort {
    private static final int R = 256; // radix
    private static final int M = 15; // cutoff for small subarrays
    private static String[] aux; // auxiliary array for distribution

    public static void main(String[] args) {
        String[] pin = {"刘持平", "洪文胜", "樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏"};

        String[] chiToEng = new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }
        int n = chiToEng.length;

        System.out.println("Unsorted Chinese array");
        print(pin, n);
        System.out.println("\n");
        System.out.println("UnsortedENglish array");
        print(chiToEng, n);
        sort(chiToEng, 0, n - 1, 0);
        System.out.println("\n");
        System.out.println("After MSD Radix Sort");
        print(chiToEng, n);

    }

    static void print(String[] str, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(str[i] + " ");
        }
        System.out.println();
    }

    private static int charAt(String s, int d) {
        if (d < s.length())
            return s.charAt(d);
        else return -1;
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) { // Sort from a[lo] to a[hi], starting at the dth character.
        if (hi <= lo + M) {
            Insertion.sort(a, lo, hi, d);
            return;
        }
        int[] count = new int[R + 2]; // Compute frequency counts.
        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;
        for (int r = 0; r < R + 1; r++) // Transform counts to indices.
            count[r + 1] += count[r];
        for (int i = lo; i <= hi; i++) // Distribute.
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        for (int i = lo; i <= hi; i++) // Copy back.
            a[i] = aux[i - lo];
        // Recursively sort for each character value.
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
    }
}