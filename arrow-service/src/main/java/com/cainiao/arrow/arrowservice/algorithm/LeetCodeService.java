package com.cainiao.arrow.arrowservice.algorithm;

import com.cainiao.arrow.arrowcommon.dto.ListNode;
import com.cainiao.arrow.arrowcommon.dto.TreeNode;
import org.springframework.cglib.core.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class LeetCodeService {


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
//            arr[minIndex] = arr[minIndex]^arr[i];
//            arr[i] = arr[minIndex]^arr[i];
//            arr[minIndex] = arr[minIndex]^arr[i];
        }
    }

    public String replaceSpace(StringBuffer str) {
        String string = str.toString();
        string = string.replace(" ","%20");
        return string;
    }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(listNode==null){
            return res;
        }
        while(listNode!=null){
            res.add(listNode.val);
            listNode = listNode.next;
        }
        Collections.reverse(res);
        return res;
    }
    /**
     * 利用栈的方式打印
     */
    public ArrayList<Integer> printListFromTailToHead2(ListNode listNode) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(listNode==null){
            return res;
        }
        Stack<Integer> stack = new Stack<>();
        while(listNode!=null){
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        //循环 出栈
        while (!stack.isEmpty()){
            res.add(stack.pop());
        }
        return res;
    }

    /**
     * 利用递归的方式打印
     */
    public ArrayList<Integer> printListFromTailToHead3(ListNode listNode) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(listNode==null){
            return res;
        }
        recursion(res,listNode);
        return res;
    }
    public void recursion(ArrayList<Integer> res,ListNode listNode) {
        if(listNode!=null){
            recursion(res,listNode.next);
            res.add(listNode.val);
        }
    }

    /**
     * 利用循环的方式打印
     * 会有重复计算，时间复杂度较高，O(2^n)
     */
    public int Fibonacci2(int n) {
        int a =1,b=1,c=0;
        if(n==0){
            return 0;
        }else if(n==1||n==2){
            return 1;
        }else{
           for(int i=3;i<=39;i++){
               c = a+b;
               a = b;
               b=c;
           }
           return c;
        }
    }
    /**
     * 利用递归的方式打印
     */
    public int Fibonacci(int n) {
        if(n==0){
            return 0;
        }else if(n==1){
            return 1;
        }else{
            return Fibonacci(n-1)+Fibonacci(n-2);
        }
    }

    /**
     * 本质是斐波那契数列，用循环做效率更高。
     */
    public int JumpFloor(int target) {
        if(target==0){
            return 0;
        }
        if(target==1){
            return 1;
        }
        if(target==2){
            return 2;
        }
        return JumpFloor(target-1)+JumpFloor(target-2);
    }




    public static void main(String[] args) {
//         int [] arr = {2,5,4,4,7,8,1};
//        LeetCodeService.selectSort(arr);

    }
}
