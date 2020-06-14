package com.cainiao.arrow.arrowservice.algorithm;

import com.cainiao.arrow.arrowcommon.dto.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class TreeTraverse {

    /**
     * 前序 根左右
     */
    public void preOrderTraverse(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + "->");
            preOrderTraverse(root.left);
            preOrderTraverse(root.right);
        }
    }

    /**
     * 中序 左根右
     */
    public void inOrderTraverse(TreeNode root) {
        if (root != null) {
            inOrderTraverse(root.left);
            System.out.print(root.val + "->");
            inOrderTraverse(root.right);
        }
    }
    /**
     * 后序 左右根
     */
    public void postOrderTraverse(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + "->");
            postOrderTraverse(root.left);
            postOrderTraverse(root.right);
        }
    }

    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        Map<Integer,Integer> map = new HashMap<>();
        int len = in.length;
        for (int i=0;i<in.length;i++){
            //值和索引对应上
            map.put(in[i],i);
        }
        return  reConstructBinaryTree(pre,in,0,len-1,0,len-1, map );
    }

    public TreeNode reConstructBinaryTree(int [] pre,int [] in,int preStart ,int preEnd,int inStart,int inEnd,Map<Integer,Integer> map ) {
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



    public static void main(String[] args) {
        int [] pre = {1,2,4,7,3,5,6,8};
        int [] in = {4,7,2,1,5,3,8,6};


    }



}
