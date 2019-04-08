package com.example.demo.java.collections;

import java.util.Random;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-04-08 17:07
 */
public class SimpleSkipList {

    private final static byte HEAD_NODE = (byte) -1;

    private final static byte DATA_NODE = (byte) 0;

    private final static byte TAIL_NODE = (byte) -1;

    private static class Node {
        private Integer value;
        private Node up, down, left, right;
        private byte bit;

        public Node(Integer value, byte bit) {
            this.value = value;
            this.bit = bit;
        }

        public Node(Integer value) {
            this(value, DATA_NODE);
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int height;
    private Random random;

    public SimpleSkipList() {
        this.head = new Node(null, HEAD_NODE);
        this.tail = new Node(null, TAIL_NODE);
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
            while(current.right.bit != TAIL_NODE && current.right.value <= element) {
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
}
