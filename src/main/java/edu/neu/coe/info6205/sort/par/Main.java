package edu.neu.coe.info6205.sort.par;

import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.LazyLogger;
import edu.neu.coe.info6205.util.WriterUtils;

import java.time.LocalDateTime;
import java.util.Arrays;


/**
 */
public class Main {

    final static LazyLogger logger = new LazyLogger(Main.class);

    static int stepCutoff = 2;
    static int stepParallelism = 2;
    static int stepSize = 2;

    static int baseParallelism = 1;
    static int baseCutoff = 1 << 12;
    static int baseSize = 1 << 18;

    public static void main(String[] args) {
        configure(args);
        doExperiments();
    }

    private static void configure(String[] args) {
        Arrays.stream(args).forEach(Main::processArg);
    }

    private static void processArg(String str) {
        if (str.startsWith("-step") || str.startsWith("-base")) {
            try {
                String[] keyValue = str.split("=");
                switch (keyValue[0]) {
                    case "-stepCutoff":
                        stepCutoff = Integer.parseInt(keyValue[1]);
                        break;
                    case "-stepParallelism":
                        stepParallelism = Integer.parseInt(keyValue[1]);
                        break;
                    case "-stepSize":
                        stepSize = Integer.parseInt(keyValue[1]);
                        break;
                    case "-baseCutoff":
                        baseCutoff = Integer.parseInt(keyValue[1]);
                        break;
                    case "-baseParallelism":
                        baseParallelism = Integer.parseInt(keyValue[1]);
                        break;
                    case "-baseSize":
                        baseSize = Integer.parseInt(keyValue[1]);
                        break;
                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                logger.warn("Unable to process argument " + str);
            }
        }
    }

    public static void doExperiments() {


        double[][][] times = new double[stepParallelism][stepCutoff][stepSize];
        int curCutoff, curParallelism, curSize;
        for (int par = 0; par < stepParallelism; ++par) {
            curParallelism = baseParallelism << par;
            for (int cutoff = 0; cutoff < stepCutoff; ++cutoff) {
                curCutoff = baseCutoff << cutoff;
                for (int size = 0; size < stepSize; ++size) {
                    curSize = baseSize << size;
                    double t = doOneExperiment(curCutoff, curSize, curParallelism);
                    times[par][cutoff][size] = t;
                }
            }
        }

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < stepParallelism * stepCutoff * stepSize; ++i) {
            sb.append(i);
            sb.append(", ");
            sb.append(baseParallelism << (i / (stepCutoff * stepSize)));
            sb.append(", ");
            sb.append(baseCutoff << (i % (stepCutoff * stepSize) / stepSize));
            sb.append(", ");
            sb.append(baseSize << (i % stepSize));
            sb.append(", ");
            sb.append(times[i / (stepCutoff * stepSize)][i % (stepCutoff * stepSize) / stepSize][i % stepSize]);
            sb.append("\n");
        }
        WriterUtils.write(String.format("./result%s.csv", LocalDateTime.now()), sb.toString());
    }

    public static double doOneExperiment(int cutoff, int arraySize) {
        return doOneExperiment(cutoff, arraySize, 1);
    }

    public static double doOneExperiment(int cutoff, int arraySize, int parallelism) {
        logger.info(String.format("Start to sort array with cutoff = %d, arraySize = %d, parallelism = %d"
                , cutoff, arraySize, parallelism));
        final double t = new Benchmark_Timer<ParSort>(
                "InsertSortBenchmarkReversed",
                ps -> {
                    ps.init();
                    return ps;
                },
                ps -> {
                    ps.sortArray();
                },
                (rs) -> {
                    return;
                }
        ).runFromSupplier(() -> new ParSort(cutoff, arraySize, parallelism), 10);
        logger.info(
                String.format("Complete sorting in mean time %fms with cutoff = %d, arraySize = %d, parallelism = %d"
                , t, cutoff, arraySize,parallelism)
        );
        return t;
    }
}
