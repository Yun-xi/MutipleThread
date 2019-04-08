package com.example.demo.java.collections.custom;

import java.util.Random;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-04-08 17:07
 */
public class SimpleSkipList {

    private final static byte HEAD_BIT = (byte) -1;

    private final static byte DATA_BIT = (byte) 0;

    private final static byte TAIL_BIT = (byte) 1;

    private static class Node {
        private Integer value;
        private Node up, down, left, right;
        private byte bit;

        public Node(Integer value, byte bit) {
            this.value = value;
            this.bit = bit;
        }

        public Node(Integer value) {
            this(value, DATA_BIT);
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int height;
    private Random random;

    public SimpleSkipList() {
        this.head = new Node(null, HEAD_BIT);
        this.tail = new Node(null, TAIL_BIT);
        head.right = tail;
        tail.left = head;
        this.random = new Random(System.currentTimeMillis());
    }

    private Node find(Integer element) {
        Node current = head;
        for (;;) {
            // 临时节点的右节点不是最后一个且右节点不大于目标值
            // 将临时节点的右节点作为当前节点
            // 就是一直向右移动，知道没有右节点了或者右节点大于了目标值
            // 临时节点为最小于目标值的节点
            while(current.right.bit != TAIL_BIT && current.right.value <= element) {
                current = current.right;
            }

            // 将临时节点的下节点作为当前节点，再进行遍历，知道没有下节点了
            if (current.down != null) {
                current = current.down;
            } else {
                break;
            }
        }
        // 就是不停向右遍历，找到最接近小于目标值的节点，再向下遍历，直到最下一层，
        // 找到的节点就是最接近小于目标值的节点
        return current;
    }

    public void add(Integer element) {
        Node nearNode = this.find(element);
        Node newNode = new Node(element);
        newNode.left = nearNode;
        newNode.right = nearNode.right;
        nearNode.right.left = newNode;
        nearNode.right = newNode;

        int currentLevel = 0;
        while (random.nextDouble() < 0.5d) {

            if (currentLevel >= height){
                height++;

                Node dumyHeaD = new Node(null, HEAD_BIT);
                Node dumyTail = new Node(null, TAIL_BIT);

                dumyHeaD.right = dumyTail;
                dumyHeaD.down = head;
                head.up = dumyHeaD;

                dumyTail.left = dumyHeaD;
                dumyTail.down = tail;
                tail.up = dumyHeaD;

                head = dumyHeaD;
                tail = dumyTail;
            }

            while ((nearNode != null) && nearNode.up == null) {
                nearNode = nearNode.left;
            }
            nearNode = nearNode.up;
            Node upNode = new Node(element);
            upNode.left = nearNode;
            upNode.right = nearNode.right;
            upNode.down = newNode;

            nearNode.right.left = upNode;
            nearNode.right = upNode;

            newNode.up = upNode;
            newNode = upNode;
            currentLevel++;
        }

        size++;
    }

    public void dumpSkipList() {
        Node temp = head;
        int i = height + 1;
        while (temp != null) {
            System.out.printf("Total [%d] height [%d]", height + 1, i--);
            Node node = temp.right;
            while (node.bit == DATA_BIT) {
                System.out.printf("->%d ", node.value);
                node = node.right;
            }
            System.out.printf("\n");
            temp = temp.down;
        }
        System.out.println("======================================");
    }

    public boolean contains(Integer element) {
        Node node = this.find(element);
        return  (node.value.equals(element));
    }

    public Integer get(Integer element) {
        Node node = this.find(element);
        return (node.value.equals(element)) ? node.value : null;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        SimpleSkipList skipList = new SimpleSkipList();
        skipList.add(100);
        skipList.dumpSkipList();

        skipList.add(1);
        skipList.dumpSkipList();

        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            skipList.add(random.nextInt(1000));
        }
        skipList.dumpSkipList();
    }
}
