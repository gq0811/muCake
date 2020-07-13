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
     * 思路：因为random不好控制，所以在每一个节点后面复制一份插在链表中，根据next.random来复制random关系
     */
    public static RandomListNode Clone(RandomListNode pHead)
    {
        if(pHead==null){
            return null;
        }
        RandomListNode start = pHead;
        while(start!=null){
            int label = start.label;
            RandomListNode newRandomListNode = new RandomListNode(label);
            //插入链表
            newRandomListNode.next = start.next;
            start.next = newRandomListNode;
            start = start.next.next;
        }
        start = pHead;
        //按照原始的关系，把random的关系挂上
        while(start!=null){
            //不一定所有节点都有random
            if(start.random!=null){
                start.next.random = start.random.next;
            }
            start = start.next.next;
        }
        start = pHead.next;
        pHead.next = null;
        RandomListNode newHead = start;
        while(start.next!=null){
            start.next = start.next.next;
            start = start.next;
        }
        return newHead;

    }
    /**
     * 一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针
     */
    public ListNode deleteDuplication(ListNode pHead)
    {
        if(pHead == null){
            return pHead;
        }
        ListNode head = pHead;
        ListNode pre = new ListNode(-1);
        pre.next = head;
        head = pre;
        while (pHead !=null && pHead.next !=null){
            if(pHead.val == pHead.next.val){
                int currVal = pHead.val;
                while(pHead!=null && pHead.val == currVal){
                    pHead = pHead.next;
                }
                pre.next = pHead;
            }else{
                pre = pre.next;
                pHead = pHead.next;
            }
        }
        return head.next;
    }

    /**
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     * 设，头节点到环入口结点的距离是a，入口结点到相遇点距离是x,相遇点入到口结点距离是t，环的长度为r，(x+t=r)
     * p1是slow指针走过的距离，p2是fast指针走过的距离，则有
     * p1 = a+x ,
     * p2 = 2p1 = p1 + nx, fast指针可能走了n圈，n>=1
     * 推出，a+x = nx
     * 再推出 ， a = (n-1)(t+x) + t ,也就是说，从"头节点"和"相遇点"同时开始走，一定会在"环的入口结点"相遇
     */
    public static ListNode EntryNodeOfLoop(ListNode pHead)
    {
        if(pHead == null){
            return null;
        }
        ListNode meetNode = findNodeOfLoop(pHead);
        //
        while(meetNode!= pHead){
            meetNode = meetNode.next;
            pHead = pHead.next;
        }
        return meetNode;
    }

    /**
     * 找到环相遇的那个节点，如果不是环则返回null，有环则返回相遇的那个节点
     */
    public static ListNode findNodeOfLoop(ListNode pHead)
    {
        if(pHead ==null){
            return null;
        }
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            //如果有环，肯定会在某一个节点遇到
            if(slow == fast){
                return slow;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        RandomListNode randomListNode = new RandomListNode(12);
        RandomListNode randomListNode2 = new RandomListNode(13);
        RandomListNode randomListNode3 = new RandomListNode(14);
        randomListNode.next = randomListNode2;
        randomListNode2.next = randomListNode3;
        randomListNode.random = randomListNode3;
        randomListNode3.random = randomListNode;
        RandomListNode res = Clone(randomListNode);
        while (res!=null){
            System.out.printf(res.label+"");
            res = res.next;
        }

    }
}
