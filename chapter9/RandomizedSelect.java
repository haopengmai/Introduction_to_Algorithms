package com.tnwb.com.IntroductiontoAlg.com.chapter9;

import java.util.Random;

public class RandomizedSelect {

    //寻找a[p,r]中任意第i小的数
    static int randomizedSelect(int a[], int p, int r, int i)
    {
        if (p == r)
            return a[p];

        int q = randomizedPartion(a, p, r);

        int k = q - p + 1;
        if (k == i)
            return a[q];
        else if (k > i)
            return randomizedSelect(a, p, q-1, i);
        else
            return randomizedSelect(a, q+1, r, i-k);
    }

    static Integer randomizedPartion(int[] a, int p, int r) {
        Integer i = new Random().nextInt(r-p+1) + p;
        exchange(a, i, r);

        return partition(a, p, r);
    }

    static Integer partition(int[] a, int p, int r) {
        Integer x = a[r];
        Integer i = p - 1;

        for(Integer j = p; j <= r - 1; j++) {
            if(a[j] <= x) {
                i = i + 1;
                exchange(a, i, j);
            }
        }

        exchange(a, i + 1, r);

        return i + 1;
    }

    // exchange content of position i and j in array
    static void exchange(int a[],int i, int j) {
        int temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int a[] = {3,2,65,6,8,9,7};

        int i = randomizedSelect(a, 0, a.length - 1, 4);

        System.out.println(i);
    }
}
