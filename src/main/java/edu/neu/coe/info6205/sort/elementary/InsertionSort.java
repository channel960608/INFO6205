/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.HelperFactory;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        for (int i = from + 1; i < to; ++i) {
            int index = i - 1;
            while (index >= 0 && helper.compare(xs, index, index + 1) > 0) {
                helper.swap(xs, index, index + 1);
                --index;
            }
        }

    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    public static void main(String... args) {

        int times = 1000;
        int arrayLength = 1;
        Config config = null;
        try {
            config = Config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int doubling = 15;
        double[][] result = new double[4][doubling];
        for (int k = 0; k < doubling; ++k) {
            arrayLength *= 2;
            final int length = arrayLength;
            Helper<Integer> helper = HelperFactory.create("InsertionSort", arrayLength, config);
            helper.init(arrayLength);
            final GenericSort<Integer> sorter = new InsertionSort<>();


            final double t1 = new Benchmark_Timer<Integer[]>(
                    "InsertSortBenchmarkRandom",
                    null,
                    t -> sorter.sort(t),
                    null
            ).runFromSupplier(() ->
                    helper.random(Integer.class, r -> r.nextInt(1000)), times);
            result[0][k] = t1;

            final double t2 = new Benchmark_Timer<Integer[]>(
                    "InsertSortBenchmarkOrdered",
                    null,
                    t -> sorter.sort(t),
                    null
            ).runFromSupplier(() -> {
                Integer[] nums = new Integer[length];
                for (int i = 0; i < nums.length; ++i) {
                    nums[i] = i;
                }
                return nums;
            }, times);
            result[1][k] = t2;

            final double t3 = new Benchmark_Timer<Integer[]>(
                    "InsertSortBenchmarkPartially",
                    null,
                    t -> sorter.sort(t),
                    null
            ).runFromSupplier(() -> {
                Integer[] nums = helper.random(Integer.class, r -> r.nextInt(1000));
                Arrays.sort(nums, nums.length / 3, nums.length * 2 / 3);
                return nums;
            }, times);
            result[2][k] = t3;

            final double t4 = new Benchmark_Timer<Integer[]>(
                    "InsertSortBenchmarkReversed",
                    null,
                    t -> sorter.sort(t),
                    null
            ).runFromSupplier(() -> {
                Integer[] nums = new Integer[length];
                for (int i = 0; i < nums.length; ++i) {
                    nums[i] = nums.length - i;
                }
                return nums;
            }, times);
            result[3][k] = t4;
        }

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < doubling; ++i) {
            sb.append(i);
            for (int j = 0; j < 4; ++j) {
                sb.append(", ");
                sb.append(result[j][i]);
            }
            sb.append("\n");
        }

        String filePath = "./data_assignment2.csv";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(sb.toString());
            System.out.println("Succeed to output data to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to write file " + filePath);
        }

    }
}
