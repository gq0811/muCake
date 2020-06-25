package com.cainiao.arrow.arrowservice.algorithm;

public class SortAlgorithm {

    /**
     * 冒泡排序
     * 不停的交换，每一轮都把最大的放最后面
     */
    public static void bubbleSort(int [] arr){
        int len = arr.length;
        for(int i=0;i<len-1;i++){
            for(int j=0;j<len-i-1;j++) {
                //j和j+1的位置的交换（如果前面的大于后面的）
                if(arr[j]>arr[j+1]){
                    arr[j] = arr[j]^arr[j+1];
                    arr[j+1] = arr[j]^arr[j+1];
                    arr[j] = arr[j]^arr[j+1];
                }
            }
        }
    }

    /**
     * 选择排序
     * 每次把最大的，和最后一位做交换
     */
    public static void selectSort(int [] arr){
        int len = arr.length;
        int minIndex ,temp;
        for(int i=0;i<len-1;i++){
            minIndex = i;
            for(int j=i+1;j<len;j++) {
                if(arr[j]<arr[minIndex]){
                    minIndex = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }




}
