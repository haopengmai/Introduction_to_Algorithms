package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter7;

public class MedianOfThreePivotQuickSort {

    static void quickSort(int[] a, int p, int r) {
        if(p < r) {
            Integer q = partition(a, p, r);
            quickSort(a, p, q-1);
            quickSort(a, q+1, r);
        }
    }

    static Integer partition(int[] a, int p, int r) {
        selectPivot(a, p, r);

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

    static void selectPivot(int a[], int p, int r) {
        int mid = p + ((r - p) >> 1);//计算数组中间的元素的下标
        //使用三数取中法选择主元

        if (a[mid] > a[p]) {  //确保a[mid] <= a[p]
            exchange(a, mid, p);
        }

        if (a[r] > a[p]) {  //确保a[r] <= a[p]
            exchange(a, p, r);
        }

        if (a[mid] > a[r]) {  //确保a[r] >= a[mid]
            exchange(a, mid, r);
        }
        //此时，a[mid] <= a[r] <= a[p]
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
        quickSort(array, 0, array.length - 1);
        printArray(array);
    }
}
