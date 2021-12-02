package edu.neu.coe.info6205.FinalProject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Stack;
import java.util.stream.IntStream;

public class TimSort<T extends Comparable<? super T>> {

    private final int CONST_FOR_GALOP = 7;
    /**
     * Initial array.
     */
    private T[] array;
    /**
     * The minimum size of subarrays in the initial array.
     */
    private int minrun = 0;
    /**
     * Stack of pairs with index of begin of subarray and its length.
     */
    private Stack<PairOfSubarray> stack;

    public static void main(String[] args) throws IOException {
        String resource = "chinese_names.txt";
        String[] pin = toEng.generateList(resource);
        String[] chiToEng = new String[pin.length];
        String[] resChiToEng = new String[pin.length];

        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }

        int count = 0;

        TimSort ts =new TimSort();
        resChiToEng = (String[]) ts.sort(chiToEng);
        for (String ss : chiToEng) {
            System.out.println(ss+"  " + resChiToEng[count]);
            count++;
        }

    }

    /**
     * Sorts array from parameter.
     *
     * @param array of T objects
     * @return sorted array
     */
    public T[] sort(T[] array) {
        assert (array != null);
        this.array = array;
        stack = new Stack<PairOfSubarray>();
        minrun = getMinrun();
        prepareArray();
        mergeInside();
        return array;
    }

    /**
     * Calculates better length of subarrays.
     *
     * @return minrun
     */
    private int getMinrun() {
        int r = 0;
        int n = array.length;
        while (n >= 64) {
            r |= n & 1;
            n >>= 1;
        }
        return n + r;
    }

    /**
     * Sequentially sorts small subarrays with length >= minrun by insertion binary sort.
     * <p>
     * Before that searches just sorted subarrays (From smallest to largest. Otherwise, reverses current subarray).
     * If length of sorted subarray is less then minrun, then supplements this subarray by next (mintun - length) elements.
     * Sorts them.
     */
    private void prepareArray() {
        int i = 0;
        int j = 0;
        while (i < array.length) {
            j = i;
            j = IntStream.range(i, array.length - 1).filter(s -> array[s].compareTo(array[s + 1]) <= 0).findFirst().orElse(array.length - 1);
            reverseInitialArray(i, j);
            j = IntStream.range(i, array.length - 1).filter(s -> array[s].compareTo(array[s + 1]) > 0).findFirst().orElse(array.length - 1);
            int localLength = Math.min(Math.max(j - i + 1, minrun), array.length - i);
            insertBinarySort(i, i + localLength);
            i += localLength;
        }
    }

    /**
     * Merges subarrays in specific order.
     * <p>
     * For more info see source link in the javadoc description of the class.
     */
    private void mergeInside() {

        int start = 0;
        do {

            int curRun = 1;
            curRun = IntStream.range(start + 1, array.length).filter(s -> array[s].compareTo(array[s - 1]) < 0).findFirst().orElse(array.length);
            stack.push(new PairOfSubarray(start, curRun - start));

            while (stack.size() > 1) {
                PairOfSubarray elementX = stack.pop();
                PairOfSubarray elementY = stack.pop();
                if (stack.size() > 0 && stack.peek().length <= elementX.length + elementY.length) {
                    if (stack.peek().length < elementX.length) {
                        PairOfSubarray elementZ = stack.pop();
                        mergeStackArrays(elementZ, elementY);
                        stack.push(elementX);
                    } else {
                        mergeStackArrays(elementY, elementX);
                    }
                } else if (elementY.length <= elementX.length) {
                    mergeStackArrays(elementY, elementX);
                } else {
                    stack.push(elementY);
                    stack.push(elementX);
                    break;
                }
            }

            start = curRun;
        } while (array.length - start != 0);


        while (stack.size() != 1)
            mergeStackArrays(stack.pop(), stack.pop());
    }

}
