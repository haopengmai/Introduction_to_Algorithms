package com.tnwb.com.IntroductiontoAlg.com.chapter15;

/**
 * 钢材切割
 */
public class SteelCut {

    /**
     * 朴素递归算法
     * @param p
     * @param n
     * @return int
     */
    public static int cutRod(int[] p, int n){
        if(n == 0) {
            return 0;
        }

        int q = -1;

        for(int i = 1; i <= n; i++) {
            q = Math.max(q, p[i - 1] + cutRod(p,n - i));
        }

        return q;
    }

    /**
     * 自顶向下的算法
     * @param p
     * @param n
     * @return
     */
    public static int memoizedCutRod(int[] p, int n) {
        int[] r = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            r[i] = -1;
        }

        return memoizedCutRodAux(p, n, r);
    }

    public static int memoizedCutRodAux(int[] p, int n, int[] r) {
        int q = -1;

        if(r[n] >= 0) {
            return r[n];
        }

        if(n == 0) {
            q = 0;
        } else if (q == -1) {
            for (int i = 1; i <= n; i++) {
                q =  Math.max(q, p[i - 1] + memoizedCutRodAux(p, n - i, r));
            }
        }

        r[n] = q;

        return q;
    }

    /**
     * 自底向上的算法
     * @param p
     * @param n
     * @return
     */
    public static int bottomUpCutRod(int[] p, int n) {
        int[] r = new int[n + 1];

        r[0] = 0;

        for (int j = 1; j <= n; j++) {
            int q = -1;

            for(int i = 1; i <= j; i++) {
                q = Math.max(q, p[i - 1] + r[j - i]);
            }

            r[j] = q;
        }

        return r[n];
    }

    public static void main(String[] args) {
        int[] price = new int[]{1,5,8,9,10,17,17,20,24,30};

        //朴素版本
        System.out.println("result 1: " + cutRod(price, price.length));

        //自顶向下的算法
        System.out.println("result 2: " + memoizedCutRod(price, price.length));

        //自底向上的算法
        System.out.println("result 3: " + bottomUpCutRod(price, price.length));
    }
}
