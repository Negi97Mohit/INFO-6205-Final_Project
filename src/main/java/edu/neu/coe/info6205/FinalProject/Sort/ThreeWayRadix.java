package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;
import edu.neu.coe.info6205.FinalProject.RegexMatch;
import edu.neu.coe.info6205.FinalProject.Utils.EnglishToChinese;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static edu.neu.coe.info6205.util.SortBenchmark.logger;

public class ThreeWayRadix {
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
        //3-Way radix Sorting
        sort(chiToEng);
        logger.info("Starting 3-Way Radix Sort Radix Sort");
        EnglishToChinese eng=new EnglishToChinese();
        String[] res=eng.swapper(pin,beSort,chiToEng);
        System.out.println("Writing to file");
        BufferedWriter br = new BufferedWriter(new FileWriter("src/main/resources/3WayRadixResult.csv"));
        StringBuilder sb = new StringBuilder();
        for (String element : res) {
            sb.append(element);
            sb.append("\n");
        }
        br.write(sb.toString());
        br.close();

    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }
    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else i++;
        }
        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
        sort(a, lo, lt - 1, d);
        if (v >= 0) sort(a, lt, gt, d + 1);
        sort(a, gt + 1, hi, d);
    }
    public static void exch(String[] s, int x, int y)
    {
        String tmp = s[x];
        s[x] = s[y];
        s[y] = tmp;
    }
}