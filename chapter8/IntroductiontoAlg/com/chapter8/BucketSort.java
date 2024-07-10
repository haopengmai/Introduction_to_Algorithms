package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter8;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BucketSort {
    public static <T> void insertionSort(T[] t, Comparator<? super T> comparator) {
        for (int j = 1; j < t.length; j++) {
            T key = t[j];
            int i = j - 1;
            while (i > -1 && comparator.compare(t[i], key) > 0) {
                t[i + 1] = t[i];
                i--;
            }
            t[i + 1] = key;
        }
    }

    public static Double[] bucketSort(Double[] A) {
        List<Double>[] B = new List[10];

        for (int i = 0; i < B.length; i++)
            B[i] = new LinkedList<>();
        for (Double d : A)
            B[(int) (d * 10)].add(d);

        Double[][] BB = new Double[10][];
        List<Double> BBB = new LinkedList<>();
        for (int i = 0; i < B.length; i++) {
            BB[i] = B[i].toArray(new Double[0]);

            insertionSort(BB[i], (o1, o2) -> (int) (Math.signum(o1 - o2)));

//            insertionSort(BB[i], new Comparator<Double>() {
//                public int compare(Double o1, Double o2) {
//                    return (int) (Math.signum(o1 - o2));
//                }
//            });

            for (Double d : BB[i])
                BBB.add(d);
        }
        return BBB.toArray(new Double[0]);
    }

    public static void main(String[] args) {
        Double[] doubles = new Double[] { 0.78, 0.17, 0.39, 0.26, 0.72, 0.94,
                0.21, 0.12, 0.23, 0.68 };
        doubles = bucketSort(doubles);
        for (Double d : doubles)
            System.out.print(d + " ");
        System.out.println();
    }

}
