package com.cainiao.arrow.arrowservice.algorithm;

public class LeetCodeService {



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

    public static void main(String[] args) {
         int [] arr = {2,5,4,4,1};
        LeetCodeService.bubbleSort(arr);
        for (int i=0;i<arr.length;i++){
            System.out.printf(arr[i]+"");
        }

    }
}
