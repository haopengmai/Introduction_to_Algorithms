package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter2;

class MergeSort {

    public static Integer step = 0;

    //归并排序
    private static void merge(Integer[] a, Integer p, Integer q, Integer r){
        Integer n1 = q - p + 1;
        Integer n2 = r - q;

        Integer[] left = new Integer[n1 + 1]; //多申请一个元素空间是为了赋值为整型最大作为边界
        Integer[] right = new Integer[n2 + 1];

        for(int i = 0; i < n1; i++){
            left[i] = a[p + i];
        }

        for(int j = 0; j < n2; j++){
            right[j] = a[q + j + 1];
        }

        left[n1] = Integer.MAX_VALUE;
        right[n2] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;

        for(int k = p; k <= r; k++) {
            if(left[i] <= right[j] && left[i] != Integer.MAX_VALUE) {
                a[k] = left[i];
                i = i + 1;
            } else if (right[j] != Integer.MAX_VALUE){
                a[k] = right[j];
                j = j + 1;
            }
        }

        //要打印排好序的子数组，所以要放在函数最后
        printSubArray(a, p, r);
    }

    private static void mergeSort(Integer[] a, Integer p, Integer r){
        Integer length = a.length;

        //首次调用不打印
        if(!length.equals(r-p+1)) {
            printSubArray(a, p, r);
        }

        if(p < r){
            Integer q = p + (r - p)/2;  //为了防止溢出
            mergeSort(a, p, q); //对左边序列进行归并排序
            mergeSort(a,q + 1, r);  //对右边序列进行归并排序
            merge(a, p, q, r);    //合并两个有序序列
        }
    }

    public static void main(String[] args) {
        Integer a[] = {5, 2, 4, 7, 1, 3, 2, 6};

        mergeSort(a, 0, 7);

        System.out.println("after sort:");
        for(int i = 0; i < 8; i++) {
            System.out.print(a[i] + " ");
        }
    }


    private static void printSubArray(Integer a[], Integer left, Integer right) {
        System.out.println("当前步骤: " + (++step));

        System.out.println("子数组为: ");
        for(Integer i = left; i <= right; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
        System.out.println("*****************");
    }
}
