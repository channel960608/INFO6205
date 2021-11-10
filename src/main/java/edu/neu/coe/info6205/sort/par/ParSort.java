package edu.neu.coe.info6205.sort.par;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
class ParSort {

    private int cutoff = 1000;
    private int arraySize = 2000000;
    private int parallelism = 1;
    private int[] array;
    private ForkJoinPool pool;
    private Random random = new Random();

    public int getCutoff() {
        return cutoff;
    }

    public void setCutoff(int cutoff) {
        this.cutoff = cutoff;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public ParSort(int cutoff, int arraySize, int parallelism) {
        this.cutoff = cutoff;
        this.arraySize = arraySize;
        this.parallelism = parallelism;
    }

    public boolean isPoolEmpty() {
        return !this.pool.hasQueuedSubmissions();
    }

    public void init() {
        this.array = new int[getArraySize()];
        int range = Integer.MAX_VALUE / 5 > getArraySize() ? Integer.MAX_VALUE : 5 * getArraySize();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(range);
        }
        this.pool = new ForkJoinPool(this.parallelism);
    }

    public void cleanPool() {
        this.pool.shutdownNow();
    }

    public void sortArray() {
        sort(array, 0, getArraySize());
    }

    public void sort(int[] array, int from, int to) {
        if (to - from < cutoff) Arrays.sort(array, from, to);
        else {
            CompletableFuture<int[]> parsort1 = parsort(array, from, from + (to - from) / 2); // TO IMPLEMENT
            CompletableFuture<int[]> parsort2 = parsort(array, from + (to - from) / 2, to); // TO IMPLEMENT
            CompletableFuture<int[]> parsort = parsort1.thenCombine(parsort2, (xs1, xs2) -> {
                int[] result = new int[xs1.length + xs2.length];
                int i = 0;
                int j = 0;
                for (int k = 0; k < result.length; k++) {
                    if (i >= xs1.length) {
                        result[k] = xs2[j++];
                    } else if (j >= xs2.length) {
                        result[k] = xs1[i++];
                    } else if (xs2[j] < xs1[i]) {
                        result[k] = xs2[j++];
                    } else {
                        result[k] = xs1[i++];
                    }
                }
                return result;
            });

            parsort.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
            parsort.join();
        }
    }

    private CompletableFuture<int[]> parsort(int[] array, int from, int to) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int[] result = new int[to - from];
                    System.arraycopy(array, from, result, 0, result.length);
                    sort(result, 0, to - from);
                    return result;
                }, this.pool
        );
    }
}