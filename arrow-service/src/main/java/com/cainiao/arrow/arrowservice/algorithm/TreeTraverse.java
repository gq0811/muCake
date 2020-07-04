package com.cainiao.arrow.arrowservice.algorithm;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.TreeNode;
import com.sun.tools.corba.se.idl.constExpr.BooleanOr;
import org.springframework.util.StringUtils;

import java.util.*;

public class TreeTraverse {

    /**
     * 前序 根左右
     */
    public static void preOrderTraverse(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + "->");
            preOrderTraverse(root.left);
            preOrderTraverse(root.right);
        }
    }

    /**
     * 中序 左根右
     */
    public static void inOrderTraverse(TreeNode root) {
        if (root != null) {
            inOrderTraverse(root.left);
            System.out.print(root.val + "->");
            inOrderTraverse(root.right);
        }
    }
    /**
     * 后序 左右根
     */
    public static void postOrderTraverse(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + "->");
            postOrderTraverse(root.left);
            postOrderTraverse(root.right);
        }
    }

    /**
     * 前序和中序重构二叉树
     */
    public static TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        Map<Integer,Integer> map = new HashMap<>();
        int len = in.length;
        for (int i=0;i<in.length;i++){
            //值和索引对应上
            map.put(in[i],i);
        }
        return  reConstructBinaryTree(pre,in,0,len-1,0,len-1, map );
    }

    public static TreeNode reConstructBinaryTree(int [] pre,int [] in,int preStart ,int preEnd,int inStart,int inEnd,Map<Integer,Integer> map ) {
        if(preStart>preEnd){
            return null;
        }
        int val = pre[preStart];
        TreeNode treeNode = new TreeNode(val);
        int index = map.get(val);
        treeNode.left = reConstructBinaryTree(pre,in,preStart+1,preStart+index-inStart,inStart,index-1,map);
        treeNode.right = reConstructBinaryTree(pre,in,preStart+index-inStart+1,preEnd,index+1,inEnd,map);
        return treeNode;
    }


    /**
     * 层次遍历
     * 利用队列
     */
    public static String  levelTraverse(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        List<String> res = new ArrayList<>();
        queue.add(root);

        while (!queue.isEmpty()){
            TreeNode currNode = queue.pop();
            if(currNode.val==-1){
                res.add("#");
            }else{
                res.add(String.valueOf(currNode.val));
            }
            if(currNode.left!=null){
                queue.add(currNode.left);
            }else if(currNode.val!=-1){
                TreeNode nullNode = new TreeNode(-1);
                queue.add(nullNode);
            }
            if(currNode.right!=null){
                queue.add(currNode.right);
            }else if(currNode.val!=-1){
                TreeNode nullNode = new TreeNode(-1);
                queue.add(nullNode);
            }
        }
        int len = res.size()-1;
        for(int i=len;i>=0;i--){
            if(res.get(i).equals("#")){
                res.remove(i);
            }else{
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(String str:res){
            sb.append(str);
            sb.append(",");
        }
        return sb.toString();
    }


    /**
     * 层次遍历重构二叉树
     * {8,8,7,9,2,#,#,#,#,4,7}
     */
    public static TreeNode reConstructBinaryTree(String str) {
        if(str.charAt(str.length()-1)==','){
            str = str.substring(0,str.length()-1);
        }
        String [] level = str.split(",");
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        String val = level[0];
        TreeNode root = new TreeNode(Integer.parseInt(val));
        queue.add(root);
        reConstructBinaryTree(level,queue,1);
        return root;
    }
    public static void reConstructBinaryTree(String [] level,LinkedList<TreeNode> queue,int index) {
        if(index>level.length-1){
            return;
        }
        TreeNode currNode = queue.pop();
        String val = level[index];
        TreeNode leftNode = null;
        if(!val.equals("#")){
            leftNode = new TreeNode(Integer.parseInt(val));
        }
        currNode.left = leftNode;
        queue.add(leftNode);
        if(index+1>level.length-1){
            reConstructBinaryTree(level,queue,index+1);
            return;
        }
        //右节点
        String val2 = level[index+1];
        TreeNode rightNode = null;
        if(!val2.equals("#")){
            rightNode = new TreeNode(Integer.parseInt(val2));
        }
        currNode.right = rightNode;
        queue.add(rightNode);
        reConstructBinaryTree(level,queue,index+2);
    }
    /**
     * 层次打印树，但是是二维list，每层的数据在一个list里面
     * 思路：用now和next分别计数，记录当前层次的个数和下一层次的个数。
     */
    public static ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> curr = new ArrayList<>();
        if(pRoot==null){
            return res;
        }
        queue.add(pRoot);
        //计数，now代表当前层次的个数，next代表下一层次的个数。
        int now = 1,next = 0;
        while (!queue.isEmpty()){
            TreeNode currNode = queue.pop();
            curr.add(currNode.val);
            now--;
            if(currNode.left!=null){
                queue.add(currNode.left);
                next++;
            }
            if(currNode.right!=null){
                queue.add(currNode.right);
                next++;
            }
            if(now==0){
                res.add(new ArrayList<>(curr));
                now = next;
                next = 0;
                curr = new ArrayList<>();
            }
        }
        return res;
    }

    /**
     * 按照之字形打印二叉树，即第一行按照从左到右的顺序打印
     * 第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推
     */
    public ArrayList<ArrayList<Integer> > PrintWithZhi(TreeNode pRoot) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> curr = new ArrayList<>();
        if(pRoot==null){
            return res;
        }
        queue.add(pRoot);
        //计数，now代表当前层次的个数，next代表下一层次的个数。
        int now = 1,next = 0,level=0;
        while (!queue.isEmpty()){
            TreeNode currNode = queue.pop();
            if (level%2==1){
                curr.add (0,currNode.val);
            }else{
                curr.add (currNode.val);
            }
            now--;
            //如果是偶数层，先加右节点。
            if (currNode.left != null) {
                queue.add(currNode.left);
                next++;
            }
            if (currNode.right != null) {
                queue.add(currNode.right);
                next++;
            }
            if(now==0){
                level++;
                res.add(new ArrayList<>(curr));
                now = next;
                next = 0;
                curr = new ArrayList<>();
            }
        }
        return res;
    }

    /**
     * 判断一棵二叉树是不是对称的。
     * 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
     */
    public static boolean isSymmetrical(TreeNode pRoot)
    {
        if(pRoot==null){
            return false;
        }
        Mirror(pRoot.left);
        return isSameTree(pRoot.left,pRoot.right);
    }
    /**
     * 判断两棵二叉树是不是一样的(结构和值都是一样的)
     */
    public static boolean isSameTree(TreeNode Root1,TreeNode Root2)
    {
        if(Root1 == null &&Root2==null){
            return true;
        }
        if(Root1 == null || Root2==null){
            return false;
        }
        if(Root1.val!=Root2.val){
            return false;
        }
        return isSameTree(Root1.left,Root2.left)&&isSameTree(Root1.right,Root2.right);
    }

    /**
     * 把一个树转成镜像树
     */
    public static void Mirror(TreeNode root) {
        if(root==null){
            return;
        }
        if(root.left==null && root.right==null){
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        Mirror(root.left);
        Mirror(root.right);
    }

    /**
     * 用先序遍历来做树的序列化和反序列化
     */
    String Serialize(TreeNode root) {
        if(root == null)
            return "";
        StringBuilder sb = new StringBuilder();
        Serialize2(root, sb);
        return sb.toString();
    }

    void Serialize2(TreeNode root, StringBuilder sb) {
        if(root == null){
            sb.append("#,");
            return;
        }
        sb.append(root.val);
        sb.append(",");
        Serialize2(root.left,sb);
        Serialize2(root.right,sb);
    }

    int serializeIndex = -1;
    /**
     * 反序列化
     */
    TreeNode Deserialize(String str) {
        if(str.length() == 0)
            return null;
        String[] strs = str.split(",");
        return Deserialize2(strs);
    }

    TreeNode Deserialize2(String[] strs) {
        serializeIndex ++;
        //如果不是空
        if(!strs[serializeIndex].equals("#")){
            int val = Integer.parseInt(strs[serializeIndex]);
            TreeNode treeNode = new TreeNode(val);
            treeNode.left = Deserialize2(strs);
            treeNode.right = Deserialize2(strs);
            return treeNode;
        }
        return null;
    }

    /**
     * root1是否包含root2的结构
     * 因为题目要求空树，不是任何树的子结构，也不包含别的子结构
     * 但是在判断是否子树关系的时候，需要判断null。
     */
    public static boolean HasSubtree(TreeNode root1,TreeNode root2) {
        //只要有一个是null，就不符合要求，返回false
        if(root1 == null || root2 == null){
            return false;
        }
        boolean flag = false;
        if(root1.val == root2.val){
            flag = isSubTree(root1,root2);
        }
        if(!flag){
            flag = HasSubtree(root1.left,root2);
        }
        if(!flag){
            flag = HasSubtree(root1.right,root2);
        }
        return flag;
    }
    public static boolean isSubTree(TreeNode root1,TreeNode root2){
        if(root1 == null && root2==null){
            return true;
        }
        if(root1 == null ){
            return false;
        }
        if(root2 == null){
            return true;
        }
        if(root1.val == root2.val){
            return isSubTree(root1.left,root2.left)&&isSubTree(root1.right,root2.right);
        }
        return false;
    }

    /**
     * 判断该数组是不是某二叉搜索树的后序遍历的结果
     */
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence ==null){
            return false;
        }
        int len = sequence.length;
        if(len ==0){
            return false;
        }
        return realVerifySquenceOfBST(sequence,0,len-1);
    }
    public boolean realVerifySquenceOfBST(int [] sequence,int start,int end) {
        if(start>=end){
            return true;
        }
        int mid = sequence[end];
        int midIndex;
        int i = start;
        while(i<=end-1&&sequence[i]<mid){
            i++;
        }
        midIndex = i;
        while(i++<=end-1){
            if(sequence[i]<mid){
                return false;
            }
        }
        return realVerifySquenceOfBST(sequence,start,midIndex-1)&&realVerifySquenceOfBST(sequence,midIndex,end-1);
    }
    /**
     * 按字典序打印出二叉树中结点值的和为输入整数的所有路径。
     * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径
     */
    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root==null){
            return res;
        }
        FindPath(root,target,res,new ArrayList<>());
        return res;
    }

    public static void FindPath(TreeNode root,int target,ArrayList<ArrayList<Integer>> res,ArrayList<Integer> currList) {
        if(root == null){
            return;
        }
        currList.add(root.val);
        //叶子节点
        if(root.left==null&&root.right==null){
            if(root.val==target){
                res.add(new ArrayList<>(currList));
            }
        }
        FindPath(root.left,target- root.val,res,currList);
        FindPath(root.right,target- root.val,res,currList);
        currList.remove(currList.size()-1);
    }
    /**
     * 该二叉搜索树转换成一个排序的双向链表
     * 思路：因为是二叉搜索树，所以用中序遍历的思路.用left,right来实现双向
     */
    static TreeNode pre = null;
    public static TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree ==null){
            return pRootOfTree;
        }
        Convert(pRootOfTree.right);
        if(pre==null){
            pre = pRootOfTree;
        }else{
            pRootOfTree.right = pre;
            pre.left = pRootOfTree;
            pre = pRootOfTree;
        }
        Convert(pRootOfTree.left);
        return pre;
    }


    public static void main(String[] args) {

        int [] pre = {1,2,4,7,3,5,6,8};
        int [] in = {4,7,2,1,5,3,8,6};
        TreeNode treeNode = TreeTraverse.reConstructBinaryTree(pre,in);

//        List<String> list = Arrays.asList(level);
        String levelStr = "5,4,#,3";
        System.out.println("levelStr:"+levelStr);
        //list转string
//        String[] strings = new String[list.size()];
//        list.toArray(strings);

        TreeNode treeNode1 = TreeTraverse.reConstructBinaryTree(levelStr);
//        preOrderTraverse(treeNode1);
//        String treeStr =  TreeTraverse.levelTraverse(treeNode1);
//        System.out.println("treeStr:"+treeStr);
//        TreeNode treeNode2 = TreeTraverse.reConstructBinaryTree(treeStr);
//        preOrderTraverse(treeNode2);
        System.out.printf("isSameTree:"+isSameTree(treeNode1,treeNode1));
//        TreeNode res = Convert(treeNode1);
//        while (res!=null){
//            System.out.printf(res.val+"");
//            res = res.right;
//        }
        //ArrayList<ArrayList<Integer>> res = FindPath(treeNode1,6);
    }



}
