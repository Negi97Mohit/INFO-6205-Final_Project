package edu.neu.coe.info6205.FinalProject;

import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.SortBenchmark;
import edu.neu.coe.info6205.util.SortBenchmarkHelper;

import java.io.FileNotFoundException;

public class toEng {
    public static void main(String[] args) throws FileNotFoundException {

    }

    public static String[] generateList(String resource) throws FileNotFoundException {
        String[] wordList= SortBenchmarkHelper.getWords(resource, SortBenchmark::lineAsList);
        return wordList;
    }

}
