package com.cainiao.arrow.arrowservice.algorithm;

import com.cainiao.arrow.arrowcommon.dto.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.TreeMap;

public class LeetCodeService {

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

    public static int NumberOf1(int n) {
        String str = Integer.toBinaryString(n);
        int count = 0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='1'){
                count++;
            }
        }
        return count;
    }

    public static double Power(double base, int exponent) {
        if(exponent==0){
            return 1;
        }
        double res = base;
        boolean isFuhsu = false;
        if(exponent<0){
            isFuhsu = true;
            exponent = -exponent;
        }
        for(int i=0;i<exponent-1;i++){
            res = res*base;
        }
        System.out.printf("");
        if(isFuhsu){
            return 1/res;
        }
        return res;
    }

    public ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        return list;
    }

    public static void testTreeMap(){
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        treeMap.put(1,1);
        treeMap.put(4,1);
        treeMap.put(5,1);
        treeMap.put(6,1);
        treeMap.put(3,1);
        for(Integer dd:treeMap.keySet()){
            System.out.printf(dd+"");
        }
        Stack<Integer> stack = new Stack<>();
        Integer node =  stack.pop();
    }

    /**
     * 判断出栈和进栈顺序是否合理
     * 第一种思路，模拟出栈，入栈的顺序，如果最后栈为空，说明是合理的。
     */
    public static boolean IsPopOrder(int [] pushA,int [] popA) {
        int count = 0;
        int count2 = 0;
        int len = pushA.length;
        Stack<Integer> stack = new Stack<Integer>();
        while(count<len && count2<len){
            //模拟入栈
            while(count<len&&pushA[count]!=popA[count2]){
                stack.push(pushA[count]);
                count++;
            }
            if(count<len&&pushA[count]==popA[count2]){
                stack.push(pushA[count]);
                count++;
            }
            //开始出栈
            while(count2<len&&stack.peek()==popA[count2]){
                count2++;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    /**
     * 判断出栈和进栈顺序是否合理
     * 第一种思路，也是模拟出入栈，直接for循环。
     */
    public static boolean IsPopOrder2(int [] pushA,int [] popA) {
        int count2 = 0;
        int len = pushA.length;
        Stack<Integer> stack = new Stack<Integer>();
        for(int i=0;i<len;i++){
            stack.push(pushA[i]);
            if(pushA[i]==popA[count2]){
                while(!stack.isEmpty()&&count2<len&&stack.peek()==popA[count2]){
                    stack.pop();
                    count2++;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        int [] in = {1,2,3,4,5};
        int [] out = {3,2,5,4,1};
        Boolean isPopOrder = IsPopOrder(in,out);
        System.out.printf("is"+isPopOrder);
    }

    /**
     * 第一种思路，可以用一个有序集合来承载栈中的元素
     * treeMap底层用红黑树实现，可以保证key的有序。不用treeSet，是因为值会有重复，需要计数
     */
    public class MinStack{
        TreeMap<Integer,Integer> treeMap = new TreeMap();
        Stack<Integer> stack = new Stack();
        public void push(int node) {
            stack.push(node);
            if(!treeMap.containsKey(node)){
                treeMap.put(node,1);
            }else{
                treeMap.put(node,treeMap.get(node)+1);
            }
        }

        public void pop() {
            Integer node =  stack.pop();
            if(treeMap.containsKey(node)){
                Integer count = treeMap.get(node)-1;
                if(count == 0){
                    treeMap.remove(node);
                }else{
                    treeMap.put(node,count);
                }
            }
        }

        public int top() {
            return stack.pop();
        }

        public int min() {
            return treeMap.firstKey();
        }
    }

    /**
     * 第二种思路，可以用一个栈来记录当前最小数的顺序。
     * 入栈的时候需要比较,minStack中存放当前stack中最小数以下所有数的顺序排列（从小到大）
     * 因为，stack中最小数以上的数都不用care，不影响min()的返回
     */
    public class MinStack2{
        Stack<Integer> stack = new Stack();
        Stack<Integer> minStack = new Stack();
        public void push(int node) {
            stack.push(node);
            if(minStack.isEmpty()||node<=minStack.peek()){
                minStack.push(node);
            }
        }

        public void pop() {
            Integer node =  stack.pop();
            if(node == minStack.peek()){
                minStack.pop();
            }
        }

        public int top() {
            return stack.pop();
        }

        public int min() {
            return minStack.peek();
        }
    }
}
