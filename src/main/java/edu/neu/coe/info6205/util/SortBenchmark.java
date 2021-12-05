/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.FinalProject.Sort.*;
import edu.neu.coe.info6205.FinalProject.*;
import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.sort.linearithmic.IntroSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_3way;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static edu.neu.coe.info6205.util.SortBenchmarkHelper.generateRandomLocalDateTimeArray;
import static edu.neu.coe.info6205.util.SortBenchmarkHelper.getWords;
import static edu.neu.coe.info6205.util.Utilities.formatWhole;

public class SortBenchmark {
    public static int threadCount=8;
    public  static ForkJoinPool threadFJP=new ForkJoinPool(threadCount);
    public final static LazyLogger logger = new LazyLogger(SortBenchmark.class);
    final static Pattern regexLeipzig = Pattern.compile("[~\\t]*\\t(([\\s\\p{Punct}\\uFF0C]*\\p{L}+)*)");
    /**
     * For (basic) insertionsort, the number of array accesses is actually 6 times the number of comparisons.
     * That's because, for each inversions, there will typically be one swap (four array accesses) and (at least) one comparison (two array accesses).
     * Thus, in the case where comparisons are based on primitives,
     * the normalized time per run should approximate the time for one array access.
     */
    private final static TimeLogger[] timeLoggersQuadratic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^2): ", (time, n) -> time / meanInversions(n) / 6 * 1e6)
    };
    private static final double LgE = Utilities.lg(Math.E);
    /**
     * For mergesort, the number of array accesses is actually 6 times the number of comparisons.
     * That's because, in addition to each comparison, there will be approximately two copy operations.
     * Thus, in the case where comparisons are based on primitives,
     * the normalized time per run should approximate the time for one array access.
     */
    public final static TimeLogger[] timeLoggersLinearithmic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n log n): ", (time, n) -> time / minComparisons(n) / 6 * 1e6)
    };
    private final Config config;

    public SortBenchmark(Config config) {
        this.config = config;
    }

    public static void main(String[] args) throws IOException {
        Config config = Config.load(SortBenchmark.class);
//        logger.info("SortBenchmark.main: " + config.get("sortbenchmark", "version") + " with word counts: " + Arrays.toString(args));
//        if (args.length == 0) logger.warn("No word counts specified on the command line");
        SortBenchmark benchmark = new SortBenchmark(config);
//        benchmark.sortIntegers(100000);
        benchmark.sortStrings(Arrays.stream(args).map(Integer::parseInt));
//        benchmark.sortLocalDateTimes(100000, config);
    }

    /**
     * Method to generate pinyin names.
     */
    public static String[] getNames() throws FileNotFoundException {
        String resource = "chinese_names.txt";
        String[] pin = ChineseToEnglish.generateList(resource);
        String[] chiToEng = new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }
        return chiToEng;
    }

    public static void radixSortMSDB(String[] arr, int nwords, int runs) {
        logger.info("SortBenchmark MSD Radix Sort with word counts: " + nwords + " with run count: " + runs);
        radixSortMSD rs = new radixSortMSD();
        final Timer timer = new Timer();
        final int zzz = 20;
        long start = Calendar.getInstance().getTimeInMillis();
        long mean = 0;
        Random rand = new Random();
        for (int i = 1; i <= runs; i++) {
            long start1 = Calendar.getInstance().getTimeInMillis();
            String[] runTest = new String[nwords];
            for (int j = 0; j < nwords; j++) {
                int index = rand.nextInt(arr.length);
                runTest[j] = arr[index];
            }
            rs.sort(runTest);
            long end1 = Calendar.getInstance().getTimeInMillis();
            mean += (end1 - start1);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        String diffTime = String.format("%,d", (end - start));
        String meanTime = String.format("%,d", mean / runs);
        logger.info("Total time MSD Radix Sort with word : " + nwords + " with run : " + runs + " (ms): " + diffTime);
        logger.info("SortBenchmark LSD Radix Sort with word counts: " + nwords + " with run count: " + runs);
        logger.info("Mean time MSD Radix Sort with word : " + nwords + " with run : " + runs + " (ms): " + meanTime);
    }

    public static void radixSortLSDB(String[] arr, int nwords, int runs) {
        logger.info("SortBenchmark.MSD Radix Sort with word counts: " + nwords + " with run count: " + runs);
        radixSortLSD rs = new radixSortLSD();
        long start = Calendar.getInstance().getTimeInMillis();
        int min = 0;
        for (String word : arr) {
            if (word.length() < min)
                min = word.length();
        }
        long mean = 0;
        Random rand = new Random();
        for (int i = 1; i <= runs; i++) {
            long start1 = Calendar.getInstance().getTimeInMillis();
            String[] runTest = new String[nwords];
            for (int j = 0; j < nwords; j++) {
                int index = rand.nextInt(arr.length);
                runTest[j] = arr[index];
            }
            rs.sort(runTest);
            long end1 = Calendar.getInstance().getTimeInMillis();
            mean += (end1 - start1);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        String diffTime = String.format("%,d", (end - start));
        String meanTime = String.format("%,d", mean / runs);
        logger.info("Total time LSD Radix Sort with word : " + nwords + " with run : " + runs + " (ms): " + diffTime);
        logger.info("Mean time LSD Radix Sort with word : " + nwords + " with run : " + runs + " (ms): " + meanTime);

    }

    /**
     * Method to run a sorting benchmark, using an explicit preProcessor.
     *
     * @param words        an array of available words (to be chosen randomly).
     * @param nWords       the number of words to be sorted.
     * @param nRuns        the number of runs of the sort to be preformed.
     * @param sorter       the sorter to use--NOTE that this sorter will be closed at the end of this method.
     * @param preProcessor the pre-processor function, if any.
     * @param timeLoggers  a set of timeLoggers to be used.
     */
    static void runStringSortBenchmark(String[] words, int nWords, int nRuns, SortWithHelper<String> sorter, UnaryOperator<String[]> preProcessor, TimeLogger[] timeLoggers) {
        new SorterBenchmark<>(String.class, preProcessor, sorter, words, nRuns, timeLoggers).run(nWords);
        sorter.close();
    }

    /**
     * Method to run a sorting benchmark using the standard preProcess method of the sorter.
     *
     * @param words       an array of available words (to be chosen randomly).
     * @param nWords      the number of words to be sorted.
     * @param nRuns       the number of runs of the sort to be preformed.
     * @param sorter      the sorter to use--NOTE that this sorter will be closed at the end of this method.
     * @param timeLoggers a set of timeLoggers to be used.
     *                    <p>
     *                    NOTE: this method is public because it is referenced in a unit test of a different package
     */
    public static void runStringSortBenchmark(String[] words, int nWords, int nRuns, SortWithHelper<String> sorter, TimeLogger[] timeLoggers) {
        runStringSortBenchmark(words, nWords, nRuns, sorter, sorter::preProcess, timeLoggers);
    }

    /**
     * This is based on log2(n!)
     *
     * @param n the number of elements.
     * @return the minimum number of comparisons possible to sort n randomly ordered elements.
     */
    static double minComparisons(int n) {
        double lgN = Utilities.lg(n);
        return n * (lgN - LgE) + lgN / 2 + 1.33;
    }

    /**
     * This is the mean number of inversions in a randomly ordered set of n elements.
     * For insertion sort, each (low-level) swap fixes one inversion, so on average there are this number of swaps.
     * The minimum number of comparisons is slightly higher.
     *
     * @param n the number of elements
     * @return one quarter n-squared more or less.
     */
    static double meanInversions(int n) {
        return 0.25 * n * (n - 1);
    }

    public static Collection<String> lineAsList(String line) {
        List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

    private static Collection<String> getLeipzigWords(String line) {
        return getWords(regexLeipzig, line);
    }

    // TODO: to be eliminated soon.
    private static Benchmark<LocalDateTime[]> benchmarkFactory(String description, Consumer<LocalDateTime[]> sorter, Consumer<LocalDateTime[]> checker) {
        return new Benchmark_Timer<>(
                description,
                (xs) -> Arrays.copyOf(xs, xs.length),
                sorter,
                checker
        );
    }

    private static void doPureBenchmark(String[] words, int nWords, int nRuns, Random random, Benchmark<String[]> benchmark) {
        final double time = benchmark.runFromSupplier(() -> Utilities.fillRandomArray(String.class, random, nWords, r -> words[r.nextInt(words.length)]), nRuns);
        for (TimeLogger timeLogger : timeLoggersLinearithmic) timeLogger.log(time, nWords);
    }

    // CONSIDER generifying common code (but it's difficult if not impossible)
    private void sortIntegers(final int n) {
        final Random random = new Random();

        // sort int[]
        final Supplier<int[]> intsSupplier = () -> {
            int[] result = (int[]) Array.newInstance(int.class, n);
            for (int i = 0; i < n; i++) result[i] = random.nextInt();
            return result;
        };

        final double t1 = new Benchmark_Timer<int[]>(
                "intArraysorter",
                (xs) -> Arrays.copyOf(xs, xs.length),
                Arrays::sort,
                null
        ).runFromSupplier(intsSupplier, 100);
        for (TimeLogger timeLogger : timeLoggersLinearithmic) timeLogger.log(t1, n);

        // sort Integer[]
        final Supplier<Integer[]> integersSupplier = () -> {
            Integer[] result = (Integer[]) Array.newInstance(Integer.class, n);
            for (int i = 0; i < n; i++) result[i] = random.nextInt();
            return result;
        };

        final double t2 = new Benchmark_Timer<Integer[]>(
                "integerArraysorter",
                (xs) -> Arrays.copyOf(xs, xs.length),
                Arrays::sort,
                null
        ).runFromSupplier(integersSupplier, 100);
        for (TimeLogger timeLogger : timeLoggersLinearithmic) timeLogger.log(t2, n);
    }

    private void sortStrings(Stream<Integer> wordCounts) throws IOException {
        logger.info("Beginning String sorts");

        // NOTE: common words benchmark
        benchmarkStringSorters(getWords("chinese_names.txt", SortBenchmark::lineAsList), 4000, 5000);

        // NOTE: Leipzig English words benchmarks (according to command-line arguments)
//        wordCounts.forEach(this::doLeipzigBenchmarkEnglish);
//
//
//        // NOTE: Leipzig Chines words benchmarks (according to command-line arguments)
//        doLeipzigBenchmark("chinese_names.txt", 5000, 1000);
    }

    private void doLeipzigBenchmarkEnglish(int x) {
        String resource = "eng-uk_web_2002_" + (x < 50000 ? "10K" : x < 200000 ? "100K" : "1M") + "-sentences.txt";
        try {
            doLeipzigBenchmark(resource, x, Utilities.round(100000000 / minComparisons(x)));
        } catch (FileNotFoundException e) {
            logger.warn("Unable to find resource: " + resource, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortLocalDateTimes(final int n, Config config) throws IOException {
        logger.info("Beginning LocalDateTime sorts");
        // TODO why do we have localDateTimeSupplier IN ADDITION TO localDateTimes?
        Supplier<LocalDateTime[]> localDateTimeSupplier = () -> generateRandomLocalDateTimeArray(n);
        Helper<ChronoLocalDateTime<?>> helper = new BaseHelper<>("DateTimeHelper", config);
        final LocalDateTime[] localDateTimes = generateRandomLocalDateTimeArray(n);

        // CONSIDER finding the common ground amongst these sorts and get them all working together.

        // NOTE Test on date using pure tim sort.
        if (isConfigBenchmarkDateSorter("timsort"))
            logger.info(benchmarkFactory("Sort LocalDateTimes using Arrays::sort (TimSort)", Arrays::sort, null).runFromSupplier(localDateTimeSupplier, 100) + "ms");

        // NOTE this is supposed to match the previous benchmark run exactly. I don't understand why it takes rather less time.
        if (isConfigBenchmarkDateSorter("timsort")) {
//            logger.info(benchmarkFactory("Repeat Sort LocalDateTimes using timSort::mutatingSort", new TimSort<>(helper)::mutatingSort, null).runFromSupplier(localDateTimeSupplier, 100) + "ms");
            // NOTE this is intended to replace the run two lines previous. It should take the exact same amount of time.
            runDateTimeSortBenchmark(LocalDateTime.class, localDateTimes, n, 100);
        }
    }

//    private void dateSortBenchmark(Supplier<LocalDateTime[]> localDateTimeSupplier, LocalDateTime[] localDateTimes, Sort<ChronoLocalDateTime<?>> dateHuskySortSystemSort, String s, int i) {
//        logger.info(benchmarkFactory(s, dateHuskySortSystemSort::sort, dateHuskySortSystemSort::postProcess).runFromSupplier(localDateTimeSupplier, 100) + "ms");
//        // NOTE: this is intended to replace the run in the previous line. It should take the exact same amount of time.
//        runDateTimeSortBenchmark(LocalDateTime.class, localDateTimes, 100000, 100, i);
//    }

    /**
     * Method to run pure (non-instrumented) string sorter benchmarks.
     * <p>
     * NOTE: this is package-private because it is used by unit tests.
     *
     * @param words  the word source.
     * @param nWords the number of words to be sorted.
     * @param nRuns  the number of runs.
     */
    public void benchmarkStringSorters(String[] words, int nWords, int nRuns) throws IOException {
//        logger.info("Testing pure sorts with " + formatWhole(nRuns) + " runs of sorting " + formatWhole(nWords) + " words");
//        Random random = new Random();
        int[] runner={1,50,100};
        int[] words1 = {250000, 500000, 999998};
        for(int runs:runner) {
            for (int word : words1) {
//            radixSortMSDB(getNames(), word, 100);
//            radixSortLSDB(getNames(), word, 100);
                sortBenchmark(new radixSortMSD(), getNames(), word,runs);
                sortBenchmark(new radixSortLSD(), getNames(), word,runs);
                sortBenchmarkTim(new TimSort(), getNames(), word,runs);
                sortBenchmark(new DualPivot(), getNames(), word, runs);
                sortBenchmark(new Husky(), getNames(), word, runs);
            }
        }


//        if (isConfigBenchmarkStringSorter("puresystemsort")) {
//            Benchmark<String[]> benchmark = new Benchmark_Timer<>("SystemSort", null, Arrays::sort, null);
//            doPureBenchmark(words, nWords, nRuns, random, benchmark);
//        }
//
//        if (isConfigBenchmarkStringSorter("mergesort")) {
//            runMergeSortBenchmark(words, nWords, nRuns, false, false);
//            runMergeSortBenchmark(words, nWords, nRuns, true, false);
//            runMergeSortBenchmark(words, nWords, nRuns, false, true);
//            runMergeSortBenchmark(words, nWords, nRuns, true, true);
//        }
//
//        if (isConfigBenchmarkStringSorter("quicksort3way"))
//            runStringSortBenchmark(words, nWords, nRuns, new QuickSort_3way<>(nWords, config), timeLoggersLinearithmic);
//
//        if (isConfigBenchmarkStringSorter("quicksort"))
//            runStringSortBenchmark(words, nWords, nRuns, new QuickSort_DualPivot<>(nWords, config), timeLoggersLinearithmic);
//
//        if (isConfigBenchmarkStringSorter("introsort"))
//            runStringSortBenchmark(words, nWords, nRuns, new IntroSort<>(nWords, config), timeLoggersLinearithmic);
//
//        // NOTE: this is very slow of course, so recommendation is not to enable this option.
//        if (isConfigBenchmarkStringSorter("insertionsort"))
//            runStringSortBenchmark(words, nWords, nRuns / 10, new InsertionSort<>(nWords, config), timeLoggersQuadratic);
    }

    public static void sortBenchmark(Sort ss, String[] arr, int nwords, int runs) throws IOException {
        String className = ss.getClass().toString().substring(ss.getClass().toString().lastIndexOf('.') + 1, ss.getClass().toString().length());
        logger.info("SortBenchmark " + className + " with word counts: " + nwords + " with run count: " + runs);
//        Sort rs= ss;
        final Timer timer = new Timer();
        final int zzz = 20;
        long start = Calendar.getInstance().getTimeInMillis();
        long mean = 0;
        Random rand = new Random();
        for (int i = 1; i <= runs; i++) {
            long start1 = Calendar.getInstance().getTimeInMillis();
            String[] runTest = new String[nwords];
            for (int j = 0; j < nwords; j++) {
                int index = rand.nextInt(arr.length);
                runTest[j] = arr[index];
            }
            ss.sort(runTest);
            long end1 = Calendar.getInstance().getTimeInMillis();
            mean += (end1 - start1);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        String diffTime = String.format("%,d", (end - start));
        String meanTime = String.format("%,d", mean / runs);
        logger.info("Total time " + className + " with word : " + nwords + " with run : " + runs + " (ms): " + diffTime);
        logger.info("Mean time " + className + " with word : " + nwords + " with run : " + runs + " (ms): " + meanTime);

    }

    public static void sortBenchmarkTim(TimSort ss, String[] arr, int nwords, int runs) throws IOException {
        String className = ss.getClass().toString().substring(ss.getClass().toString().lastIndexOf('.') + 1, ss.getClass().toString().length());
        logger.info("SortBenchmark " + className + " with word counts: " + nwords + " with run count: " + runs);
//        Sort rs= ss;
        TimSort tim = new TimSort();
        final Timer timer = new Timer();
        final int zzz = 20;
        long start = Calendar.getInstance().getTimeInMillis();
        long mean = 0;
        Random rand = new Random();
        for (int i = 1; i <= runs; i++) {
            long start1 = Calendar.getInstance().getTimeInMillis();
            String[] runTest = new String[nwords];
            for (int j = 0; j < nwords; j++) {
                int index = rand.nextInt(arr.length);
                runTest[j] = arr[index];
            }
            tim.sort(runTest);
            long end1 = Calendar.getInstance().getTimeInMillis();
            mean += (end1 - start1);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        String diffTime = String.format("%,d", (end - start));
        String meanTime = String.format("%,d", mean / runs);
        logger.info("Total time " + className + " with word : " + nwords + " with run : " + runs + " (ms): " + diffTime);
        logger.info("Mean time " + className + " with word : " + nwords + " with run : " + runs + " (ms): " + meanTime);

    }

    /**
     * Method to run instrumented string sorter benchmarks.
     * <p>
     * NOTE: this is package-private because it is used by unit tests.
     *
     * @param words  the word source.
     * @param nWords the number of words to be sorted.
     * @param nRuns  the number of runs.
     */
    void benchmarkStringSortersInstrumented(String[] words, int nWords, int nRuns) {
        logger.info("Testing with " + formatWhole(nRuns) + " runs of sorting " + formatWhole(nWords) + " words" + (config.isInstrumented() ? " and instrumented" : ""));
        Random random = new Random();


        if (isConfigBenchmarkStringSorter("puresystemsort")) {
            Benchmark<String[]> benchmark = new Benchmark_Timer<>("SystemSort", null, Arrays::sort, null);
            doPureBenchmark(words, nWords, nRuns, random, benchmark);
        }

        if (isConfigBenchmarkStringSorter("mergesort")) {
            runMergeSortBenchmark(words, nWords, nRuns, false, false);
            runMergeSortBenchmark(words, nWords, nRuns, true, false);
            runMergeSortBenchmark(words, nWords, nRuns, false, true);
            runMergeSortBenchmark(words, nWords, nRuns, true, true);
        }

        if (isConfigBenchmarkStringSorter("quicksort3way"))
            runStringSortBenchmark(words, nWords, nRuns, new QuickSort_3way<>(nWords, config), timeLoggersLinearithmic);

        if (isConfigBenchmarkStringSorter("quicksort"))
            runStringSortBenchmark(words, nWords, nRuns, new QuickSort_DualPivot<>(nWords, config), timeLoggersLinearithmic);

        if (isConfigBenchmarkStringSorter("introsort"))
            runStringSortBenchmark(words, nWords, nRuns, new IntroSort<>(nWords, config), timeLoggersLinearithmic);

        // NOTE: this is very slow of course, so recommendation is not to enable this option.
        if (isConfigBenchmarkStringSorter("insertionsort"))
            runStringSortBenchmark(words, nWords, nRuns / 10, new InsertionSort<>(nWords, config), timeLoggersQuadratic);
    }

    private void runMergeSortBenchmark(String[] words, int nWords, int nRuns, Boolean insurance, Boolean noCopy) {
        Config x = config.copy(MergeSort.MERGESORT, MergeSort.INSURANCE, insurance.toString()).copy(MergeSort.MERGESORT, MergeSort.NOCOPY, noCopy.toString());
        runStringSortBenchmark(words, nWords, nRuns, new MergeSort<>(nWords, x), timeLoggersLinearithmic);
    }

    private void doLeipzigBenchmark(String resource, int nWords, int nRuns) throws IOException {
        benchmarkStringSorters(getWords(resource, SortBenchmark::getLeipzigWords), nWords, nRuns);
        if (isConfigBoolean(Config.HELPER, BaseHelper.INSTRUMENT))
            benchmarkStringSortersInstrumented(getWords(resource, SortBenchmark::getLeipzigWords), nWords, nRuns);
    }

    @SuppressWarnings("SameParameterValue")
    private void runDateTimeSortBenchmark(Class<?> tClass, ChronoLocalDateTime<?>[] dateTimes, int N, int m) throws IOException {
//        final SortWithHelper<ChronoLocalDateTime<?>> sorter = new TimSort<>();
//        @SuppressWarnings("unchecked") final SorterBenchmark<ChronoLocalDateTime<?>> sorterBenchmark = new SorterBenchmark<>((Class<ChronoLocalDateTime<?>>) tClass, (xs) -> Arrays.copyOf(xs, xs.length), sorter, dateTimes, m, timeLoggersLinearithmic);
//        sorterBenchmark.run(N);
    }

    private boolean isConfigBenchmarkStringSorter(String option) {
        return isConfigBoolean("benchmarkstringsorters", option);
    }

    private boolean isConfigBenchmarkDateSorter(String option) {
        return isConfigBoolean("benchmarkdatesorters", option);
    }

    private boolean isConfigBoolean(String section, String option) {
        return config.getBoolean(section, option);
    }
}