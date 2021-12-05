package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.util.SortBenchmark;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import static edu.neu.coe.info6205.util.SortBenchmark.getNames;

public class MultiThreadSorting {
    public static void main(String[] args) throws IOException {
        int[] words1 = {250000,500000,999998};
        int[] threadCount={4};
        for(int thread:threadCount) {
            SortBenchmark.threadCount=thread;
            SortBenchmark.threadFJP=new ForkJoinPool(SortBenchmark.threadCount);
            for (int word : words1) {
                SortBenchmark.sortBenchmark(new RadixSortMSD(), getNames(), word, 10);
                SortBenchmark.sortBenchmark(new RadixSortLSD(), getNames(), word, 10);
                SortBenchmark.sortBenchmarkTim(new TimSort(), getNames(), word, 10);
                SortBenchmark.sortBenchmark(new DualPivot(), getNames(), word, 10);
                SortBenchmark.sortBenchmark(new Husky(), getNames(), word, 1);
            }
        }
    }
}