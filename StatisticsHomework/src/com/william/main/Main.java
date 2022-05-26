package com.william.main;

import com.william.data.Constant;
import com.william.util.Process;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import java.io.*;

/**
 * 主方法入口。
 *
 * @author WilliamLi
 * @version: 1.0
 * @date 2022/5/25 22:17
 */
public class Main {

    /**
     * 卡方分布计算工具，需要commons-math3-3.6.1jar包依赖。
     */
    private static ChiSquaredDistribution chiSquaredDistribution;

    /**
     * 标准输出流重定向。按需启用。
     */
    private static void redirect(){
        File resultFile = new File(".\\src\\com\\william\\result\\result.txt");
        if (!resultFile.exists()) {
            try {
                resultFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(resultFile, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.setOut(printStream);
        System.setErr(printStream);
    }

    public static void main(String[] args) {

        //redirect();

        int[][] resultArr = new int[3][4];
        double[][] expectArr = new double[3][4];

        Process process = new Process();
        String[][] result = process.analyse_Data();

        for (int i = 2; i < result.length; i++) {
            System.out.println(result[i][16] + "\t" + result[i][19]);
        }

        System.out.println("-----------------------------");
        System.out.println("observation value: ");

        for (int i = 0; i < Constant.CORRECT_RATE.length; i++) {
            for (int j = 0; j < Constant.FINISH_TIME.length; j++) {
                for (int z = 2; z < result.length; z++) {
                    if (result[z][16].equals(Constant.FINISH_TIME[j]) && result[z][19].equals(Constant.CORRECT_RATE[i])) {
                        resultArr[i][j]++;
                    }
                }
            }
        }

        for (int[] v : resultArr) {
            for (int r : v) {
                System.out.print(r);
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println("-----------------------------");
        System.out.println("marginal distribution: ");

        int[] Xs = new int[4];
        int[] Ys = new int[3];
        int sum = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                Xs[i] += resultArr[j][i];
                Ys[j] += resultArr[j][i];
                sum += resultArr[j][i];
            }
        }

        System.out.print("x: ");
        for (int v : Xs) {
            System.out.print(v + " ");
        }

        System.out.println();

        System.out.print("y: ");
        for (int v : Ys) {
            System.out.print(v + " ");
        }

        System.out.println();

        System.out.println("-----------------------------");

        System.out.println("sum: " + sum);

        System.out.println("-----------------------------");
        System.out.println("expect value: ");

        double chi_squire = 0;
        int df = (resultArr.length - 1) * (resultArr[0].length - 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                expectArr[i][j] = (float) Xs[j] * Ys[i] / sum;
                System.out.print(expectArr[i][j] + "\t");
                chi_squire += Math.pow(resultArr[i][j] - expectArr[i][j], 2) / expectArr[i][j];
            }
            System.out.println();
        }

        System.out.println("-----------------------------");

        System.out.println("chi-squire test statistic: " + chi_squire);

        System.out.println("-----------------------------");

        chiSquaredDistribution = new ChiSquaredDistribution(df);
        double p_val = 1 - chiSquaredDistribution.cumulativeProbability(chi_squire);
        System.out.println("p-val: " + p_val);

        System.out.println("-----------------------------");

        System.out.println("conclusion: ");
        if (p_val<Constant.ALPHA){
            System.out.println("Our P-val="+p_val+", alpha="+Constant.ALPHA+". Because our P-val is less than our alpha level "+Constant.ALPHA+", " +
                    "we reject H0. We do have convincing evidence that there is association between finish time and correct rate.");
        } else {
            System.out.println("Our P-val="+p_val+", alpha="+Constant.ALPHA+". Because our P-val is higher than our alpha level "+Constant.ALPHA+", " +
                    "we fail to reject H0. We do not have convincing evidence that there is association between finish time and correct rate.");
        }
    }
}
