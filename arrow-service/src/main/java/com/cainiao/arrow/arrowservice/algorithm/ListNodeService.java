package com.cainiao.arrow.arrowservice.algorithm;

import com.cainiao.arrow.arrowcommon.dto.ListNode;
import com.cainiao.arrow.arrowcommon.dto.RandomListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 链表相关操作
 */
public class ListNodeService {

    /**
     * 输出该链表中倒数第k个结点。
     */
    public static ListNode FindKthToTail(ListNode head, int k) {
        Stack<ListNode> stack = new Stack<ListNode>();
        while(head!=null){
            stack.push(head);
            head = head.next;
        }
        if(stack.isEmpty()){
            return head;
        }
        if(k>stack.size()){
            return null;
        }
        while(k-->=2){
            stack.pop();
        }
        return  stack.pop();
    }


    public ListNode ReverseList(ListNode head) {
        if(head ==null || head.next==null){
            return head;
        }
        ListNode p = head;
        ListNode q = head.next;
        ListNode r = head.next.next;
        head.next = null;
        while (r !=null){
            q.next = p;
            p = q;
            q = r;
            r= r.next;
        }
        q.next = p;
        return q;
    }

    /**
     * 创建链表
     */
    public static ListNode createListNode(List<Integer> list,int i){
        if(i<=list.size()-1){
            ListNode listNode = new ListNode(list.get(i));
            listNode.next = createListNode(list,i+1);
            return listNode;
        }
        return null;
    }

    /**
     * 每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针random指向一个随机节点
     * 请对此链表进行深拷贝，并返回拷贝后的头结点
     */
    public RandomListNode Clone(RandomListNode pHead)
    {

        return null;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        ListNode listNode = ListNodeService.createListNode(list,0);
//        while (listNode!=null){
//            System.out.printf(listNode.val+"");
//            listNode = listNode.next;
//        }
        System.out.printf(ListNodeService.FindKthToTail(listNode,2)+"");
    }
}
