package com.cainiao.arrow.arrowservice.algorithm;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.ListNode;
import org.omg.CORBA.MARSHAL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

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
    /**
     * 字符串中字符的所有排列
     *
     */
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        if(str == null || str.equals("")){
            return arrayList;
        }
        char[] charArray = str.toCharArray();
        TreeSet<String> set = new TreeSet<>();
        realPermutation(charArray,0,set);
        return new ArrayList<>(set);
    }

    /**
     * 递归去交换
     * 用TreeSet保证结果不重复,且有顺序
     * 用String不好的地方，是需要不停的转char数组，再转回string
     * TreeSet对象也会创建很多，浪费内存，还有可能OOM
     */
    public Set<String> realPermutation(String str,int start) {
        TreeSet<String> set = new TreeSet<>();
        if(start>=str.length()-1){
            set.add(str);
            return set;
        }
        char[] charArray = str.toCharArray();
        for(int i=start;i<str.length();i++){
            swap(charArray,i,start);
            String currStr = String.valueOf(charArray);
            set.addAll(realPermutation(currStr,start+1));
            //交换完，要换回来，再按之前的顺序来
            swap(charArray,i,start);
        }
        return set;
    }
    /**
     * 递归去交换
     * 用TreeSet保证结果不重复,且有顺序
     */
    public void realPermutation(char[] charArray,int start,TreeSet<String> set) {
        if(start>=charArray.length-1){
            set.add(String.valueOf(charArray));
            return;
        }
        for(int i=start;i<charArray.length;i++){
            swap(charArray,i,start);
            realPermutation(charArray,start+1,set);
            //交换完，要换回来，再按之前的顺序来
            swap(charArray,i,start);
        }
    }

    /**
     * 交换charArray中两个位置的数据
     */
    private void swap(char[] charArray, int i, int j){
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }

    /**
     * 给定一个数target，分成若干部分，得到最大的乘积
     * 例如：8，可以分成 2，3，3，得到最大的乘积18
     * 个人思路：把target尽量等分，可以保证乘积最大
     */
    public int cutRope(int target) {
        int max = 0;
        for(int i=target;i>=1;i--){
            //把数分成i份
            int avg = target/i;
            int left = target%i;
            int curr = intPow(avg+1,left)*intPow(avg,i-left);
            max = Math.max(curr, max);
        }
        return max;
    }
    /**
     * 还有个思路，可以转换为数学问题，因为都是整数，所以尽量的用3去分割，可以使乘积最大。
     *
     */
    public int cutRope2(int n) {
        if(n==2){
            return 1;
        }else if(n==3){
            return 2;
        }
        if(n%3==0){
            return (int)Math.pow(3,n/3);
        }else if(n%3==1){
            return 4*(int)Math.pow(3,n/3-1);
        }else {
            return 2*(int)Math.pow(3,n/3);
        }
    }
    private int intPow(int n,int count){
        int res = 1;
        while (count-->0){
            res = res*n;
        }
        return res;
    }
    /**
     * 一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于k的格子
     * 本质上是海岛问题，不是动态规划的问题。
     */
    public int movingCount(int threshold, int rows, int cols)
    {
        boolean [][] visited = new boolean[rows][cols];
        return movingCount(threshold,rows,cols,0,0,visited);
    }

    public int movingCount(int threshold, int rows, int cols,int i,int j,boolean [][] visited)
    {
        //如果越界，或者已经访问过，
        if(i<0 || i>=rows || j<0 || j>=cols || visited[i][j] || checkSum(i)+checkSum(j)>threshold){
            return 0;
        }
        visited[i][j] = true;
        return  1+ movingCount(threshold,rows,cols,i-1,j,visited)+
                movingCount(threshold,rows,cols,i+1,j,visited)+
                movingCount(threshold,rows,cols,i,j-1,visited)+
                movingCount(threshold,rows,cols,i,j+1,visited);
    }

    //返回位数和
    private int checkSum(int n){
        if(n<10){
            return n;
        }
        int res = 0;
        while(n>0){
            int curr = n%10;
            res+=curr;
            n = n/10;
        }
        return res;
    }

    /**
     * 是否存在一条包含某字符串所有字符的路径
     * char[] matrix给的是一个一维数组（打平成一行）
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        boolean [][] visited = new boolean[rows][cols];
        for(int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                if(hasPath(matrix,rows,cols,i,j,0,str,visited)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * i,j为起始位置
     * count记住当前位置
     */
    public boolean hasPath(char[] matrix, int rows, int cols,int i,int j, int count,char[] str,boolean [][] visited)
    {
        int index = i*cols+j;
        if(i<0 || i>=rows || j<0 || j>=cols || visited[i][j] || matrix[index]!=str[count]){
            return false;
        }
        if(count==str.length-1){
            return true;
        }
        visited[i][j] = true;
        //到这个地方时，代表之前匹配上了，count都要加1
        boolean res = hasPath(matrix,rows,cols,i-1,j,count+1,str,visited)||
               hasPath(matrix,rows,cols,i+1,j,count+1,str,visited)||
                hasPath(matrix,rows,cols,i,j-1,count+1,str,visited)||
                hasPath(matrix,rows,cols,i,j+1,count+1,str,visited);
        if(res){
            return true;
        }
        //和海岛问题不同，起始的位置是变化的，所以visited要重置。
        visited[i][j] = false;
        return false;
    }

    /**
     * 滑动窗口的最大值，
     * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}
     * 第一种思路，每次都求窗口中的最大值，可以用treeMap维护，也可以实时排序求得最大值
     */
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        ArrayList<Integer> res =new ArrayList<>();
        if(num == null|| size<=0 || size>num.length){
            return res;
        }
        int len = num.length;
        for(int i=0;i<size;i++){
            if(!treeMap.containsKey(num[i])){
                treeMap.put(num[i],1);
            }else{
                treeMap.put(num[i],treeMap.get(num[i])+1);
            }
        }
        res.add(getMaxVal(treeMap));
        for(int i=size;i<len;i++){
            if(!treeMap.containsKey(num[i])){
                treeMap.put(num[i],1);
            }else{
                treeMap.put(num[i],treeMap.get(num[i])+1);
            }
            int count = treeMap.get(num[i-size])-1;
            if(count<=0){
                treeMap.remove(num[i-size]);
            }else{
                treeMap.put(num[i-size],treeMap.get(num[i-size])-1);
            }
            res.add(getMaxVal(treeMap));
        }
        return res;
    }

    /**
     * 滑动窗口的最大值，
     * 第二种思路：用队列保存最大值所在的索引（有点像最小栈）,保证最大值在最前面
     */
    public ArrayList<Integer> maxInWindows2(int [] num, int size){
        ArrayList<Integer> res =new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        if(num == null|| size<=0 || size>num.length){
            return res;
        }
        int len = num.length;
        for(int i=0;i<size-1;i++){
            //在最大值前面不可能是当前窗口的最大值了
            while (!linkedList.isEmpty()&&num[i]>num[linkedList.getLast()]){
                linkedList.removeLast();
            }
            linkedList.add(i);
        }
        for(int i=size-1;i<len;i++){
            while (!linkedList.isEmpty()&&num[i]>num[linkedList.getLast()]){
                linkedList.removeLast();
            }
            linkedList.add(i);
            //check最大值索引，如果失效了，则删掉
            int index = linkedList.getFirst();
            if(i-size+1>index){
                linkedList.removeFirst();
            }
            res.add(num[linkedList.getFirst()]);
        }
        return res;
    }
    private Integer getMaxVal(TreeMap<Integer,Integer> treeMap){
        return treeMap.lastKey();
    }

    /**
     * 得到一个数据流中的中位数
     * Insert用来插入数据
     */
    //用LinkedList来维护一个顺序队列
    LinkedList<Integer> medianList = new LinkedList<>();
    public void Insert(Integer num) {
         if(medianList.isEmpty()){
             medianList.add(num);
         }else{
             if(num<=medianList.getFirst()){
                 medianList.addFirst(num);
             }else if(num>=medianList.getLast()){
                 medianList.addLast(num);
             }else{
                 //放到合适的位置
                 int index = 0;
                 while (medianList.get(index)<num){
                     index++;
                 }
                 medianList.add(index,num);
             }
         }
        System.out.printf(JSON.toJSONString(medianList));
    }
    /**
     * 得到中位数
     */
    public Double GetMedian() {
        if(medianList == null||medianList.size()==0){
            return null;
        }
        int len = medianList.size();
        //奇数
        if(len%2==1){
           return Double.valueOf(medianList.get(len/2));
        }
        Double t1 = Double.valueOf(medianList.get(len/2));
        Double t2 = Double.valueOf(medianList.get(len/2-1));
        return (t1+t2)/2;
    }

    /**
     * 2.还可以用一个小顶堆，和一个大顶堆，中位数就取各自的顶端
     * 大顶堆：5，3，2 小顶堆：6，7，8
     */
    PriorityQueue<Integer> minPriorityQueue = new PriorityQueue<>();
    PriorityQueue<Integer> maxPriorityQueue  = new PriorityQueue<Integer>(15, new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    });
    //用计数，控制均匀放到两个堆中
    int medianCount = 0;
    public void Insert2(Integer num) {
        if(medianCount%2==0){
            //往最小堆中添加的时候，不直接加，而是在大堆中混一圈之后，把最大放最小堆中，
            // 这样保证最小堆中的数比最大堆中的都大。
            maxPriorityQueue.add(num);
            minPriorityQueue.add(maxPriorityQueue.poll());
        }else{
            minPriorityQueue.add(num);
            maxPriorityQueue.add(minPriorityQueue.poll());
        }
        medianCount++;
    }
    /**
     * 得到中位数
     */
    public Double GetMedian2() {
        if(medianCount%2==1){
            return Double.valueOf(minPriorityQueue.peek());
        }else{
            Double t1 = Double.valueOf(maxPriorityQueue.peek());
            Double t2 = Double.valueOf(minPriorityQueue.peek());
            return (t1+t2)/2;
        }
    }

    /**
     * 实现一个函数用来找出字符流中第一个只出现一次的字符。
     * 例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。
     * 当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"
     * 思路，用一个int数组来表示char元素对应的顺序，-1代表该char已经出现过，
     */
    //用一个int数组来表示char元素对应的顺序
    int [] charArr = new int[256];
    int appearingOnceIndex = 1;
    public void Insert(char ch)
    {
        if(charArr[ch]==0){
            charArr[ch] = appearingOnceIndex++;
        }else{
            //说明ch已经出现过了
            charArr[ch] = -1;
        }
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        int curr = Integer.MAX_VALUE;
        char candidate = '#';
        for(int i=0;i<256;i++){
            if(charArr[i]!=0 && charArr[i]!=-1 && charArr[i]<curr){
                curr = charArr[i];
                candidate = (char)i;
            }
        }
        return candidate;
    }
    /**
     * 函数用来匹配包括'.'和'*'的正则表达式。
     * 模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
     * 在本题中，匹配是指字符串的所有字符匹配整个模式。
     * 例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
     */
    public boolean match(char[] str, char[] pattern)
    {
        if(str ==null || pattern == null){
            return false;
        }
        int strIndex = 0;
        int patternIndex = 0;
        return realMatch(str,pattern,strIndex,patternIndex);
    }

    /**
     * 递归去匹配
     */
    public boolean realMatch(char[] str, char[] pattern ,int strIndex,int patternIndex)
    {
        //都匹配到最后位置了
        if(strIndex == str.length && patternIndex == pattern.length){
            return true;
        }
        //str到最后了，但是pattern还没到最后
        if(strIndex != str.length && patternIndex == pattern.length){
            return false;
        }
        //先判断*，因为会有'.*'的情况
        if(patternIndex<pattern.length-1 && pattern[patternIndex+1]=='*'){
            if((strIndex!=str.length&&str[strIndex]==pattern[patternIndex]) || (strIndex!=str.length&&pattern[patternIndex]=='.')){
                 return realMatch(str,pattern,strIndex,patternIndex+2) ||    //匹配0个，
                         realMatch(str,pattern,strIndex+1,patternIndex+2) || //尝试先匹配1个，
                         realMatch(str,pattern,strIndex+1,patternIndex); // 继续按当前pattern的方式匹配
            }else{
                //此时str[strIndex]和pattern[patternIndex]是不同的字符，例如"a"和"b*"，此时pattern中"b*"就废掉了，往下匹配
                 return realMatch(str,pattern,strIndex,patternIndex+2);
            }
        }
        //如果相等，或者有'.',那么当前位置匹配，匹配后面位置的字符串
        if((strIndex!=str.length&&str[strIndex]==pattern[patternIndex]) || (strIndex!=str.length&&pattern[patternIndex]=='.')){
            return realMatch(str,pattern,strIndex+1,patternIndex+1);
        }
        return false;
    }

    /**
     * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
     * 只有一个出现一次的数时，很好办，用异或的方式就可以得到最终的值
     * 有两个，就得想办法分开。
     */
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        if (array == null){
            return;
        }
        int res = 0;
        for(int i=0;i<array.length;i++){
            res = res ^ array[i];
        }
        int index = findFirst1(res);
        for(int i=0;i<array.length;i++) {
            if(isFirstType(array[i],index)){
                num1[0] = num1[0] ^ array[i];
            }else{
                num2[0] = num2[0] ^ array[i];
            }
        }
    }

    //找到num1、num2第一个不同的位置即可。
    private int findFirst1(int num){
        String binaryStr = Integer.toBinaryString(num);
        int i=0;
        while(binaryStr.charAt(binaryStr.length()-1-i)!='1'){
            i++;
        }
        return i;
    }

    //check在index这个位置，是不是等于1,等于就是第一种类型
    private boolean isFirstType(int num,int index){
        return ((num>>index)&1) == 1;
    }

    /**
     * 找到第一个不重复的字母，例如："google"，则返回"l"
     */
    public int FirstNotRepeatingChar(String str) {
        if(str==null || "".equals(str)){
            return -1;
        }
        int [] arr =new int['z'-'A'+1];
        for(int i=0;i<arr.length;i++){
            arr[i] = -1;
        }
        for(int i=0;i<str.length();i++){
            int ch = str.charAt(i)-'A';
            if(arr[ch]==-2){
                continue;
            }
            if(arr[ch]==-1){
                arr[ch] = i;
            }else{
                arr[ch] = -2;
            }
        }
        int min = str.length()+1;
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=-1 && arr[i]!=-2){
                if(arr[i] <min){
                    min = arr[i];
                }
            }
        }
        return min>str.length()?-1:min;
    }

    /**
     *  输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     *  例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     */
    public String PrintMinNumber(int [] numbers) {
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<numbers.length;i++){
           list.add(numbers[i]);
        }
        Collections.sort(list,new Comparator<Integer>(){
            @Override
            public int compare(Integer t1,Integer t2){
                String s1 = t1 +"" +t2;
                String s2 = t2 +"" +t1;
                return s1.compareTo(s2);
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for(Integer t:list){
            stringBuilder.append(t);
        }
        return stringBuilder.toString();
    }

    /**
     *  数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     *  例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。 返回2
     */
    //第一种思路，利用排序
    public int MoreThanHalfNum_Solution(int [] array) {
        if(array ==null ||array.length==0){
            return 0;
        }
       int count = 1;
       int candidate = array[0];
       for(int i=0;i<array.length;i++){
           if(array[i] == candidate){
               count++;
           }else{
               count--;
           }
           if(count < 0){
               candidate = array[i];
               count =1;
           }
       }
       return candidate;
    }
    public int MoreThanHalfNum_Solution2(int [] array) {
        return 0;
    }

    /**
     *  给一个数组，返回它的最大连续子序列的和
     *  {6,-3,-2,7,-15,1,2,2}
     *  思路：动态规划
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        if(array == null || array.length==0){
            return 0;
        }
        int len = array.length;
        //以i位置为结尾的最大连续子序列的和
        int [] dp = new int[len];
        int max = Integer.MIN_VALUE;
        for(int i=0;i<len;i++){
            if(i == 0){
                dp[i] = array[i];
            }else{
                //比较，如果array[i]加上之前的比自身还少，也就是dp[i-1]<0,就重新开始计数了。因为是最大连续
                dp[i] = Math.max(dp[i-1]+array[i],array[i]);
                max = Math.max(dp[i],max);
            }
        }
        return max;
    }


    /**
     *  求出1~n的整数中1出现的次数
     *  思路1：转换为字符串的方式做
     *  思路2：归纳为数学问题
     *  设k = n % 10，即为不完整阶梯段的数字
     *  归纳式为：(n / 10) * 1 + (if(k > 1) 1 else if(k < 1) 0 else k - 1 + 1)
     *  设k = n % 100，即为不完整阶梯段的数字
     *  归纳式为：(n / 100) * 10 + (if(k > 19) 10 else if(k < 10) 0 else k - 10 + 1)
     *  设k = n % 1000
     *  归纳式为：(n / 1000) * 100 + (if(k >199) 100 else if(k < 100) 0 else k - 100 + 1)
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        int sum = 0;
        int count = 10;
        do{
            sum += (n/count)*(count/10);
            int k = n % (count/10);
            if(k > count/10*2-1){
                sum += count/10;
            }else if(k<count/10){
                sum +=0;
            }else{
                sum +=(k-count/10+1);
            }
            count*=10;
        }while(n/count!=0);
        return sum;
    }


    /**
     * 把只包含质因子2、3和5的数称作丑数（Ugly Number）。
     * 例如6、8都是丑数，但14不是，因为它包含质因子7。
     * 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
     * 思路：基于的理念就是，一个丑数是由另一个丑数
     */
    public int GetUglyNumber_Solution(int index) {
        PriorityQueue<Integer> priorityQueue2 = new PriorityQueue<>();
        PriorityQueue<Integer> priorityQueue3 = new PriorityQueue<>();
        PriorityQueue<Integer> priorityQueue5 = new PriorityQueue<>();
        int [] uglyArr = new int [index];
        uglyArr[0]=1;
        for(int i=0;i<index-1;i++){
            int ugly = uglyArr[i];
            priorityQueue2.add(ugly*2);
            priorityQueue3.add(ugly*3);
            priorityQueue5.add(ugly*5);
            int min = Math.min(Math.min(priorityQueue2.peek(),priorityQueue3.peek()),priorityQueue5.peek());
            if(min == priorityQueue2.peek()){
                priorityQueue2.poll();
            }
            if(min == priorityQueue3.peek()){
                priorityQueue3.poll();
            }
            if(min == priorityQueue5.peek()){
                priorityQueue5.poll();
            }
            uglyArr[i+1] = min;
        }
        return uglyArr[index-1];
    }
    /**
     * 也可以直接用指针来表示，模拟队列的效果
     */
    public int GetUglyNumber_Solution2(int index) {
        if(index<=0){
            return 0;
        }
        int [] uglyArr = new int [index];
        int p1=0,p2=0,p3=0;
        int count = 0;
        uglyArr[0] = 1;
        while(count<index-1){
            int min = Math.min(Math.min(uglyArr[p1]*2,uglyArr[p2]*3),uglyArr[p3]*5);
            if(min == uglyArr[p1]*2){
                p1++;
            }
            if(min == uglyArr[p2]*3){
                p2++;
            }
            if(min == uglyArr[p3]*5){
                p3++;
            }
            uglyArr[++count] = min;
        }
        return uglyArr[index-1];
    }

    public static void main(String[] args) {
        LeetCodeService leetCodeService = new LeetCodeService();
        int [] in = {1,2,3,4,5};
        int [] out = {3,2,5,4,1};
        char [] arr = {'c','c'};
        leetCodeService.Insert(5);
        leetCodeService.Insert(2);
        leetCodeService.Insert(3);
        int [] arr2 = new int[]{1,2,3,2,2,2,5,4,2};

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
