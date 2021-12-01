package edu.neu.coe.info6205.FinalProject;

import edu.neu.coe.huskySort.sort.huskySort.PureHuskySort;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoder;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.huskySort.util.LazyLogger;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.SortBenchmark;

import java.io.FileNotFoundException;

public class benchmarkingSorts<X extends Comparable<X>> {
    public static void main(String[] args) throws FileNotFoundException {
        String resource="chinese_names.txt";
        String[] pin=toEng.generateList(resource);
        String[] chiToEng=new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }

        pureHuskyBenchmark(chiToEng);

    }

    public static void  pureHuskyBenchmark(String[] arr){
        logger.info("Pure Husky Sort Initiated");
        PureHuskySort ps=new PureHuskySort(HuskyCoderFactory.asciiCoder, false, false);

        ps.sort(arr);


    }

    private final static LazyLogger logger = new LazyLogger(PureHuskySort.class);

}
