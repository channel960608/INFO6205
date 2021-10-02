package edu.neu.coe.info6205.union_find;

import edu.neu.coe.info6205.util.WriterUtils;

import java.util.Random;

public class HWQUPC_Solution {

    private static Random random;

    public static int count() {
        return -1;
    }

    public static int connectTimes(int n) {
        UF h = new UF_HWQUPC(n, true);
        int count = n;
        if (null == random) {
            random = new Random();
        }
        int time = 0;
        while (count > 1) {
            ++time;
            int left = random.nextInt(n), right = random.nextInt(n);
            if (!h.isConnected(left, right)) {
                --count;
                h.union(left, right);
            }
        }
        return time;
    }

    public static double connectTimesMuiltiple(int n, int m) {
        double sum = 0;
        for (int i = 0; i < m; ++i) {
            sum += HWQUPC_Solution.connectTimes(n);
        }
        return sum / m;
    }

    public static void count(int n, int m) {
        int[] x = new int[n];
        double[][] y = new double[n][1];
        for (int i = 1; i <= n; ++i) {
            x[i - 1] = i;
            y[i - 1][0] = connectTimesMuiltiple(i, m);
            System.out.println(String.format("Experiment # of sites is: %s, repeat time is: %s, connect times is %s ", n, m, y[i - 1][0]));
        }
        WriterUtils.write("./data_assignment3.csv", x, y);
    }

    public static void main(String... args) {
        count(10240, 100);
    }

}
