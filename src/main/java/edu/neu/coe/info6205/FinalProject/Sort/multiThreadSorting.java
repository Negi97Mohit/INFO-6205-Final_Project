package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.util.SortBenchmark;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static edu.neu.coe.info6205.util.SortBenchmark.getNames;

public class multiThreadSorting {
    public static void main(String[] args) throws IOException {
        int[] words1 = {250000,500000,999998};
        int[] threadCount={4};
        for(int thread:threadCount) {
            SortBenchmark.threadCount=thread;
            SortBenchmark.threadFJP=new ForkJoinPool(SortBenchmark.threadCount);
            for (int word : words1) {
                SortBenchmark.sortBenchmark(new radixSortMSD(), getNames(), word, 100);
                SortBenchmark.sortBenchmark(new radixSortLSD(), getNames(), word, 100);
//                SortBenchmark.sortBenchmark(new TimSort(), getNames(), word, 100);
                SortBenchmark.sortBenchmark(new DualPivot(), getNames(), word, 100);
                SortBenchmark.sortBenchmark(new Husky(), getNames(), word, 100);
            }
        }
    }
}