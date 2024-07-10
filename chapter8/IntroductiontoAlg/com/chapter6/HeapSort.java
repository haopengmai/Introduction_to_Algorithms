package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter6;

public class HeapSort {

    public static final Integer NUM = 10;

    static int parent(int i) {
        return i >> 1;
    }

    static int left(int i) {
        return i << 1;
    }

    static int right(int i) {
        return (i << 1) + 1;
    }

    static void printArray(int a[], int length) {
        int i;

        for(i = 1; i <= length; i++ )
        {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    static void maxHeapify(int a[],int i,int heapSize) {
        int l = left(i);
        int r = right(i);
        int largest;

        if (l <= heapSize && a[l] > a[i] )
        {
            largest = l;
        } else {
            largest = i;
        }

        if(r <= heapSize && a[r] > a[largest])
        {
            largest = r;
        }

        if(largest != i)
        {
            exchange(a, largest, i);
            maxHeapify(a, largest, heapSize);
        }
    }

    static void buildMaxHeap(int a[],int length) {
        int i;
        for(i = length / 2; i >= 1; i--)
        {
            maxHeapify(a, i, length);
        }
    }

    static void heapSort(int a[],int length) {
        int i;
        buildMaxHeap(a,length);

        for(i = length; i >= 2; i--)
        {
            exchange(a,1, i);
            maxHeapify(a, 1, i - 1);
        }
    }

    // exchange content of position i and j in array
    static void exchange(int a[],int i, int j) {
        int temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int array[] = {0,16,14,10,8,7,9,3,2,4,1};  //多申请一个是第0个位置不用
        heapSort(array,NUM);
        printArray(array,NUM);
    }
}
