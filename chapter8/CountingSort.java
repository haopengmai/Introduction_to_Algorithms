package com.tnwb.com.IntroductiontoAlg.com.chapter8;

public class CountingSort {
    static void countingSort(int[] a, int[] b, int k) {
        int[] c = new int[k + 1];

        //c为Count数组，用于计数，下标表示范围从0到k的值
        for(int i = 0; i < c.length; i++) {
            c[i] = 0;
        }

        //遍历a数组计数，a中每出现一个值，c中以这个值为下标的值加1，表示该值在a中出现几次
        for(int j = 0; j < a.length; j++) {
            c[a[j]] = c[a[j]] + 1;
        }

        //累加c数组，这样可以解决计数排序的不稳定问题
        //累加后c数组的值代表以当前c的下标m，在b中出现的位置下标是c[m]
        for(int i = 1; i <= k; i++) {
            c[i] = c[i] + c[i-1];
        }

        //逆序遍历数组a，根据累加后的数组c将a中每个元素正确填入b中正确的位置中
        for(int j = a.length-1; j >= 0; j--) {
            b[c[a[j]] - 1] = a[j];  //注意这里因为数字下标是从0开始的，所以要将c[a[j]]减一
            c[a[j]] = c[a[j]] - 1;
        }
    }

    static void printArray(int a[]) {
        int i;

        for(i = 0; i < a.length; i++ )
        {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] a = {2,5,3,0,2,3,0,3};
        int[] b = new int[a.length];

        for(int i = 0; i < b.length; i++) {
            b[i]  = 0;
        }

        countingSort(a, b, 5);

        printArray(b);
    }
}
