package edu.neu.coe.info6205.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Caspar
 * @date 2021/10/1 17:34
 */
public class WriterUtils {

    public static void write(String filePath, String data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(data);
            System.out.println("Succeed to output data to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to write file " + filePath);
        }
    }

    public static void write(String filePath, int[] x, double[][] y) {
        StringBuilder sb = new StringBuilder("");
        // TODO check the length of input array
        for (int i = 0; i < x.length; ++i) {
            sb.append(x[i]);
            sb.append(", ");
            for (double d : y[i]) {
                sb.append(d);
                sb.append(", ");
            }
            sb.append("\n");
        }
        write(filePath, sb.toString());
    }


}
