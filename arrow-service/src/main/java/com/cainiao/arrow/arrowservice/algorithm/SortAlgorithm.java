package com.cainiao.arrow.arrowservice.algorithm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SortAlgorithm {

    /**
     * 冒泡排序  O(n^2) 稳定
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
     * 选择排序  O(n^2) 不稳定
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
            swap(arr,i,minIndex);
        }
    }

    /**
     * 插入排序 O(n^2) 稳定
     * 候选的数，一直在和前面的有序序列做交换
     */
    public static void InsertSort(int [] arr){
        int len = arr.length;
        for(int i=1;i<len;i++){
            if(arr[i]<arr[i-1]){
                int curr = i;
                while(curr>0 && arr[curr]<arr[curr-1]){
                    swap(arr,curr,curr-1);
                    curr--;
                }
            }
        }
    }
    /**
     * 希尔排序，在插入排序的基础上，增加gap的概念 O(n^1.3) 不稳定
     * 候选的数，一直在和前面的有序序列做交换
     */
    public static void shellSort(int [] arr){
        int len = arr.length;
        for(int gap = len/2;gap>0;gap = gap/2){
            for(int i=gap;i<len;i++){
                if(arr[i]<arr[i-gap]){
                    int curr = i;
                    while(curr>0 && arr[curr]<arr[curr-gap]){
                        swap(arr,curr,curr-gap);
                        curr--;
                    }
                }
            }
        }
    }
    /**
     * 归并排序，分治的思想 O(n^logn) 稳定
     */
    public static void mergeSort(int [] arr){
        int len = arr.length;
        merge(arr,0,len-1);
    }
    public static void merge(int [] arr,int start,int end){
        if(start<end){
            int mid = (start+end)/2;
            merge(arr,start,mid);
            merge(arr,mid+1,end);
            mergeArr(arr,start,mid,end);
        }
    }
    //合并数组，前面是start～mid，后面是mid+1～end
    public static void mergeArr(int [] arr,int start,int mid,int end){
        //用一个数组来承接一下
        int [] temp = new int[end-start+1];
        int i=start,j=mid+1,k=0;
        while (i<=mid && j<=end){
            if(arr[i]<=arr[j]){
                temp[k++] = arr[i++];
            }else{
                temp[k++] = arr[j++];
            }
        }
        while (i<=mid){
            temp[k++] = arr[i++];
        }
        while(j<=end){
            temp[k++] = arr[j++];
        }
        for(int p=start;p<=end;p++){
            arr[p] = temp[p-start];
        }
    }


    private static void swap (int [] arr,int i,int minIndex){
        int temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }
    public static void main(String[] args) {
        int [] arr = new int[]{364,637,341,406,747,995,234,971,571,219,993,407,416,366,315,301,601,650,418,355,460,505,360,965,516,648,727,667,465,849,455,181,486,149,588,233,144,174,557,67,746,550,474,162,268,142,463,221,882,576,604,739,288,569,256,936,275,401,497,82,935,983,583,523,697,478,147,795,380,973,958,115,773,870,259,655,446,863,735,784,3,671,433,630,425,930,64,266,235,187,284,665,874,80,45,848,38,811,267,575};
        mergeSort(arr);
        System.out.println(JSON.toJSONString(arr));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fdf","vdfvdf");
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(jsonObject);
        ThreadLocal threadLocal2 = new ThreadLocal();
        threadLocal.set("frevrv");
        System.out.println(threadLocal.get());
    }

}
