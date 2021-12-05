package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.Utils.EnglishToChinese;
import edu.neu.coe.info6205.FinalProject.RegexMatch;
import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;
import edu.neu.coe.info6205.graphs.BFS_and_prims.StdRandom;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.ForkJoinPool;

import static edu.neu.coe.info6205.util.SortBenchmark.logger;


public class DualPivot<X extends Comparable<X>>  extends Sort {
    public static int threadCount=1;
    static ForkJoinPool threadFJP=new ForkJoinPool(threadCount);
    private static void show(String[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }


    public  void sort(String[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray a[lo .. hi] using dual-pivot quicksort
    private static void sort(String[] a, int lo, int hi) {
        if (hi <= lo) return;

        // make sure a[lo] <= a[hi]
        if (less(a[hi], a[lo])) exch(a, lo, hi);

        int lt = lo + 1, gt = hi - 1;
        int i = lo + 1;
        while (i <= gt) {
            if       (less(a[i], a[lo])) exch(a, lt++, i++);
            else if  (less(a[hi], a[i])) exch(a, i, gt--);
            else                         i++;
        }
        exch(a, lo, --lt);
        exch(a, hi, ++gt);

        // recursively sort three subarrays
        sort(a, lo, lt-1);
        if (less(a[lt], a[gt])) sort(a, lt+1, gt-1);
        sort(a, gt+1, hi);

        assert isSorted(a, lo, hi);
    }






    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }


    private static void exch(String[] a, int i, int j) {
        String swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(String[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(String[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }









    public static void main(String[] args) throws Exception {

        String resource = "chinese_names.txt";
        String[] pin = ChineseToEnglish.generateList(resource);
        System.out.println("PIN");
        System.out.println(pin);
        String[] chiToEng = new String[pin.length];

        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = RegexMatch.getPingYin(pin[i]);
        }
        int count = 0;
        for (String da : chiToEng) {
            System.out.println(da + pin[count]);
            count++;
        }
        System.out.println(count);
//        --------------------------------------------------------------------------------
//        ArrayList<Integer> arr = new ArrayList<Integer>();
//        arr.add(3);
//        arr.add(43);
//        arr.add(20);
//        arr.add(35);
//        arr.add(111);
//        arr.add(99);
//        arr.add(5);
//        for (int i = 0; i < arr.size(); i++) {
//            System.out.println(arr.get(i));
//        }

//        Partition<X> send = new Partition<X>(arr, 0, arr.size());
//      -------------------------------------------------------------------------------------

        new DualPivot().sort(chiToEng);
        show(chiToEng);
        for (int i=0; i< chiToEng.length; i++){
            System.out.println(chiToEng[i]);
        }

        String[] beSort = new String[chiToEng.length];
        for (int i = 0; i < chiToEng.length; i++)
            beSort[i] = chiToEng[i];
        logger.info("Starting MSD Radix Sort");
        new RadixSortMSD().sort(chiToEng);
        EnglishToChinese eng = new EnglishToChinese();
        String[] res = eng.swapper(pin, beSort, chiToEng);
        System.out.println("Writing to file");
        BufferedWriter br = new BufferedWriter(new FileWriter("src/main/resources/DualPivotResult.csv"));
        StringBuilder sb = new StringBuilder();
        for (String element : res) {
            sb.append(element);
            sb.append("\n");
        }
        br.write(sb.toString());
        br.close();

    }


}
