package com.cainiao.arrow.arrowservice.algorithm;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.TreeNode;
import com.sun.tools.corba.se.idl.constExpr.BooleanOr;

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
     * 层次遍历
     * 利用队列
     */
    public static  List<String>  levelTraverse(TreeNode root) {
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
            if(currNode.left!=null){
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
        return res;
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
     * 层次遍历重构二叉树
     * {8,8,7,9,2,#,#,#,#,4,7}
     */
    public static TreeNode reConstructBinaryTree(String [] level) {
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
     * 把一个树转成镜像树
     */
    public void Mirror(TreeNode root) {
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
    public static void main(String[] args) {

        int [] pre = {1,2,4,7,3,5,6,8};
        int [] in = {4,7,2,1,5,3,8,6};
        TreeNode treeNode = TreeTraverse.reConstructBinaryTree(pre,in);


        String [] level = new String[]{"1","2","6","3","4","5","-1"};
        String [] level1 = new String[]{"8","9","2"};

        List<String> list = Arrays.asList(level);
        //list转string
        String[] strings = new String[list.size()];
        list.toArray(strings);

        TreeNode treeNode1 = TreeTraverse.reConstructBinaryTree(level);
        TreeNode treeNode2= TreeTraverse.reConstructBinaryTree(level1);
        ArrayList<ArrayList<Integer>> res = FindPath(treeNode1,6);
        System.out.printf(JSON.toJSONString(res));
    }



}