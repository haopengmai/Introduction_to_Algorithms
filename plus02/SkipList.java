package com.tnwb.com.IntroductiontoAlg.com.plus02;


public class SkipList {
    /**
     * 最高层数16层
     */
    private static final int MAX_LEVEL = 16;

    /**
     * 每层上升概率0.5
     */
    private static final double P = 0.5;


    /**
     * 当前有效层数
     */
    private int level;

    /**
     * 伪首节点，不存放任何键值对
     */
    private Node first;

    public SkipList() {
        first = new Node(null, null, MAX_LEVEL);
    }


    public Integer get(Integer key) {
        keyCheck(key);

        Node node = first;
        for (int i = level - 1; i >= 0; i--) {
            /**
             * 当下一个节点非空，
             * 且当前键小于下一个节点的键时，
             * while循环继续
             */
            while (node.nexts[i] != null
                    && key < node.nexts[i].key) {
                node = node.nexts[i];
            }

            //相等直接返回，否则i减1，走下面一层
            if (key == node.nexts[i].key) return node.nexts[i].value;
        }
        return null;
    }

    public Integer put(Integer key, Integer value) {
        keyCheck(key);

        Node node = first;
        Node[] prevs = new Node[level];
        for (int i = level - 1; i >= 0; i--) {
            /**
             * 当下一个节点非空，
             * 且当前键小于下一个节点的键时，
             * while循环继续
             */
            while (node.nexts[i] != null
                    &&  key < node.nexts[i].key) {
                node = node.nexts[i];
            }
            if (key == node.nexts[i].key) { // 节点是存在的
                Integer oldValue = node.nexts[i].value;
                node.nexts[i].value = value;
                return oldValue;
            }
            //prevs用来记录每一层要插入前的节点
            prevs[i] = node;
        }

        // 新节点的层数,随机产生
        int newLevel = randomLevel();
        // 添加新节点
        Node newNode = new Node(key, value, newLevel);
        // 设置前驱和后继
        for (int i = 0; i < newLevel; i++) {
            if (i >= level) {
                first.nexts[i] = newNode;
            } else {
                newNode.nexts[i] = prevs[i].nexts[i];
                prevs[i].nexts[i] = newNode;
            }
        }

        // 计算跳表的最终层数
        level = Math.max(level, newLevel);

        return null;
    }

    public Node remove(Integer key) {
        keyCheck(key);

        Node node = first;
        Node[] prevs = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0; i--) {
            /**
             * 当下一个节点非空，
             * 且当前键小于下一个节点的键时，
             * while循环继续
             */
            while (node.nexts[i] != null
                    && key < node.nexts[i].key) {
                node = node.nexts[i];
            }
            prevs[i] = node;
            if (key == node.nexts[i].key) exist = true;
        }
        if (!exist) return null;

        // 需要被删除的节点
        Node removedNode = node.nexts[0];

        // 设置后继
        for (int i = 0; i < removedNode.nexts.length; i++) {
            prevs[i].nexts[i] = removedNode.nexts[i];
        }

        // 删除后更新跳表的层数
        int newLevel = level;
        while (--newLevel >= 0 && first.nexts[newLevel] == null) {
            level = newLevel;
        }

        return removedNode;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    private void keyCheck(Integer key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null.");
        }
    }


    private static class Node {
        Integer key;
        Integer value;
        Node[] nexts;

        public Node(Integer key, Integer value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }
        @Override
        public String toString() {
            return key + ":" + value + "_" + nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + level + "层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node node = first;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();

        for (int i = 0; i < 20; i++) {
            skipList.put(i, i + 10);
        }

        System.out.println(skipList.toString());
    }
}

