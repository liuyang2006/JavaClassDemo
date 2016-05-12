/**
 *
 */
package ch01.factn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class FactTool {

    public static void main(String[] args) {
        int N = 10;
        Scanner scanner = new Scanner(System.in);

        System.out.print("该程序计算N！，请输入N：");
        N = scanner.nextInt();

        FactTool tool = new FactTool();
        System.out.println(tool.getAllResults(N));
        System.out.println(tool.getAllResultsByHtml(N));

    }

    public FactTool() {
        super();
    }

    public String getAllResults(int n) {
        String l_retByInt = mulResultsByInt(n);
        String l_retByDouble = mulResultsByDouble(n);
        String l_retByBigInteger = mulResultsByBigInteger(n);

        String l_outStr = n + "!(int)= " + l_retByInt + "\n" + n + "!(double)= " + l_retByDouble + "\n" + n
                + "!(BigInteger)= " + "(共" + l_retByBigInteger.length() + "位): " + l_retByBigInteger;

        return l_outStr;
    }

    public String getAllResultsByHtml(int n) {
        StringBuffer ret = new StringBuffer();

        String retByInt = mulResultsByInt(n);
        String retByDouble = mulResultsByDouble(n);
        String retByBigInteger = mulResultsByBigInteger(n);

        ret.append("<table border=\"1\">\n");

        ret.append("<tr><td>数据类型</td><td>计算结果</td><tr>\n<tr><td>");

        ret.append(n + "!(int)");
        ret.append("</td><td>");

        ret.append(retByInt);
        ret.append("</td>\n<tr><td>");

        ret.append(n + "!(double)");
        ret.append("</td><td>");

        ret.append(retByDouble);
        ret.append("</td>\n<tr><td>");

        ret.append(n + "!(BigInteger)");
        ret.append("</td><td style=\"border: width:100px;word-wrap:break-all;\">");

        ret.append(retByBigInteger);
        ret.append("</td></tr>\n</table>");


        return ret.toString();
    }

    public String mulResultsByBigInteger(int n) {
        BigInteger mul = BigInteger.valueOf(1L);
        for (int i = 1; i <= n; i++) {
            mul = mul.multiply(BigInteger.valueOf(i));
        }
        return mul.toString();
    }

    public String mulResultsByDouble(int n) {
        double mul = 1.0;
        for (int i = 1; i <= n; i++) {
            mul = mul * i; // Double.valueOf(mul).isInfinite()
        }
        return String.valueOf(mul);
    }

    public String mulResultsByInt(int n) {
        int mul = 1;

        for (int i = 1; i <= n; i++) {
            mul = mul * i; // mul * i == 0

        }
        return String.valueOf(mul);
    }


}
