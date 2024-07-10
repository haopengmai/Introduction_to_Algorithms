package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter15;

import java.util.ArrayList;

public class LCS {

    /**
     * 看整数m的第i位是不是1
     * @param m
     * @param i
     * @return
     */
    static boolean isOne(int m, int i) {
        int bit = i - 1;
        int k = (m & (1 << bit));
        return k > 0;
    }

    /**
     * 通过m构造出x的一个子串
     * @param str
     * @param m
     * @return
     */
    static String getSubString(String str, int m) {
        String s = "";
        int len = str.length();

        for (int i = 1; i <= len; i++) {
            if (isOne(m, i)) { //看m的第i位是不是1
                s = str.charAt(len - i) + s;
            }
        }

        return s;
    }

    /**
     * 判断sub是否是anotherStr的一个子序列
     * @param anotherStr
     * @param sub
     * @return
     */
    static boolean isSubSequence(String anotherStr, String sub) {
        int subIndex = 0;

        for (int i = 0; i < anotherStr.length(); i++) {
            if (sub.charAt(subIndex) == anotherStr.charAt(i)) {
                subIndex++;
                if (subIndex == sub.length()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获得两个字符串的lcs,采用暴力解法，指数级
     * @param str
     * @param anotherStr
     * @return
     */
    static String getLCS(String str, String anotherStr) {
        int len = str.length();

        /**
         * m是指数级，
         * 利用0-m范围内的每个数，
         * 根据它们的比特位去构造子串，
         * 为0不取该位置的字符，
         * 为1取该位置的字符
         */
        int m = (1 << len) - 1;
        int maxLen = 0;
        String longest = null;

        for (int i = m; i > 0; i--) {
            //每个m代表x中的一个子串序列
            String sub = getSubString(str, i);
            boolean isSeq = isSubSequence(anotherStr, sub);
            if (isSeq) {
                if (sub.length() > maxLen) {
                    longest = sub;
                    maxLen = sub.length();
                }
            }
        }

        return longest;
    }


    /**
     把c矩阵和b矩阵放入容器中返回容器。
     */
    static ArrayList<int[][]> lcsLength(char[] x, char[] y) {
        ArrayList<int[][]> ls = new ArrayList<>();

        int m = x.length;
        int n = y.length;

        int[][] c = new int[m+1][n+1];
        int[][] b = new int[m+1][n+1];

        //c[i][j]表示xi和yi的LCS的长度，当i或者j等于0，序列的长度也为0.
        for (int i = 0; i < c.length; i++) {
            c[i][0] = 0;
        }
        for (int i = 0; i < c[0].length; i++) {
            c[0][i] = 0;
        }

        for(int i=1; i <= m; i++) {
            for(int j=1; j <=n; j++) {
                /*这里和书上不一样，
                因为书上的伪代码数组下标从1开始，
                实际代码数组下标从0开始
                 */
                if(x[i-1] == y[j-1]) {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = 1;
                } else if(c[i-1][j]>=c[i][j-1]){
                    c[i][j] = c[i-1][j];
                    b[i][j] = 2;
                } else {
                    c[i][j] = c[i][j-1];
                    b[i][j] = 3;
                }
            }
        }

        ls.add(b);
        ls.add(c);
        return ls;
    }

    /**
     重构最优解LCS
     */
    static void printLCS(int[][] b, char[] x, int i, int j) {
        if(i==0 || j==0)
            return;

        if(b[i][j] == 1 ) {
            //1,y序列长度相同且最长公共子序列是Xn和Yn的情况，存在相同的元素
            printLCS(b,x,i-1,j-1);
            System.out.print(x[i-1]);
        } else if(b[i][j] == 2) {
            //2,y两个序列长度不同且最长公共子序列是Xn-1和Yn的情况
            printLCS(b,x,i-1,j);
        } else{
            //3,y两个序列长度不同且最长公共子序列是x和Yn-1的情况
            printLCS(b,x,i,j-1);
        }
    }

    public static void main(String[] args) {
        String str = "ABCBDAB";
        String anotherStr = "BDCABA";

        System.out.println(getLCS(str, anotherStr));

        char[] x = str.toCharArray();
        char[] y = anotherStr.toCharArray();

        ArrayList<int[][]> ls = lcsLength(x,y);
        int[][] b = ls.get(0);

        //构造LCS
        printLCS(b,x,x.length,y.length);
    }
}
