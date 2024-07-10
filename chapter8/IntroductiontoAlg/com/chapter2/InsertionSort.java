package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter2;

public class InsertionSort {

    //插入排序
    public static void insertionSort(Integer[] a) {
        int length = a.length;

        for (int j = 1; j < length; j++) {
            Integer key = a[j];

            Integer i = j - 1;
            while (i >= 0 && a[i] > key) {
                a[i + 1] = a[i];
                i = i - 1;
            }

            a[i + 1] = key;
        }
    }

    public static void main(String[] args) {
        Integer a[] = {5, 2, 4, 6, 1, 3};

        insertionSort(a);

        System.out.println("after sort:");
        for(int i = 0; i < 6; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
