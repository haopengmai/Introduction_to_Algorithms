package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter7;

import java.util.Random;

public class RandomQuickSort {

    static void randomizedQuickSort(int[] a, int p, int r) {
        if(p < r) {
            Integer q = randomizedPartion(a, p, r);
            randomizedQuickSort(a, p, q-1);
            randomizedQuickSort(a, q+1, r);
        }
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

    static void printArray(int a[]) {
        int i;

        for(i = 0; i < a.length; i++ )
        {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int array[] = {2,8,7,1,3,5,6,4};
        randomizedQuickSort(array, 0, array.length - 1);
        printArray(array);
    }
}
