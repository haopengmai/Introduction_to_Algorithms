package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter12;

public class BinarySearchTree implements BinaryTreeInfo {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public BinarySearchTree() {
        this.root = null;
    }

    public BinarySearchTree(Node root) {
        this.root = root;
    }

    //前序遍历二叉树
    public void preorderTreeWalk(Node x) {
        if(x != null) {
            System.out.print(x.key + " ");
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    //中序遍历二叉树
    public void inorderTreeWalk(Node x) {
        if(x != null) {
            inorderTreeWalk(x.left);
            System.out.print(x.key + " ");
            inorderTreeWalk(x.right);
        }
    }

    //后序遍历二叉树
    public void postorderTreeWalk(Node x) {
        if(x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.print(x.key + " ");
        }
    }

    //在二叉搜索树中查询某个值，递归版本
    public Node treeSearch(Node x, Integer k) {
        if(x == null || k == x.key) {
            return x;
        }

        if(k < x.key) {
            return treeSearch(x.left, k);
        } else {
            return treeSearch(x.right, k);
        }
    }

    //在二叉搜索树中查询某个值，循环版本
    public Node iterativeTreeSearch(Node x, Integer k) {
        while(x != null && k != x.key) {
            if(k < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        return x;
    }

    //在二叉搜索树中查找包含最小值的节点
    public Node treeMinimum(Node x) {
        while (x.left != null) {
            x = x.left;
        }

        return x;
    }

    //在二叉搜索树中查找包含最大值的节点
    public Node treeMaximum(Node x) {
        while (x.right != null) {
            x = x.right;
        }

        return x;
    }

    //查找节点x的后继节点
    public Node treeSuccessor(Node x) {
        if(x.right != null) {  //x的右子树不为空，找右子树的最小值
            return treeMinimum(x.right);
        }

        Node y = x.parent;
        while(y != null && x == y.right) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    //查找节点x的前驱节点
    public Node treePredeceessor(Node x) {
        if(x.left != null) {
            return treeMaximum(x.left);
        }

        Node y = x.parent;
        while(y != null && x == y.left) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    //二叉搜索树的插入操作
    public void treeInsert(BinarySearchTree t, Node z) {
        Node y = null;
        Node x = t.root;
        while(x != null) {
            y = x;
            if(z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;
        if(y == null) {  //说明现在是棵空树
            t.root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    public void transplant(BinarySearchTree t, Node u, Node v) {
        if(u.parent == null) {
            t.root = v;
        } else if(u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        if(v != null) {
            v.parent = u.parent;
        }
    }

    //算法导论书上的删除节点操作
    public void treeDelete(BinarySearchTree t, Node z) {

        if (z.left == null) {
            //z的左孩子为空，用z的右孩子替换z，此时z的右孩子可以为空，也可以不为空
            transplant(t, z, z.right);
        } else if (z.right == null) {
            //z仅有一个孩子且其为左孩子，用z的左孩子替换z
           transplant(t, z , z.left);
        } else {
            //z有两个孩子，找z的后继节点
            Node y = treeMinimum(z.right);
            //y作为后继节点，只可能有右孩子，不可能有左孩子
            if(y.parent != z) {
                //用以y的右孩子为根的树代替以y为根的树
                transplant(t, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            //用以y为根的树代替以z为根的树
            transplant(t, z, y);
            y.left = z.left;
            y.left.parent = y;
        }
    }

    //更容易看懂的删除节点操作
    public void remove(Node node) {
        if (node == null) return;

        if (node.left != null && node.right != null) { // 度为2的节点
            // 找到后继节点
            Node s = treeSuccessor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            // 为删除后继节点做准备
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
        }
    }

    //节点类定义
    static class Node {
        private Integer key;  //节点值
        private Node parent;  //父节点
        private Node left;   //左孩子
        private Node right;   //右孩子

        public Node() {

        }

        public Node(Integer key) {
            this.key = key;
        }

        public Node(Integer key, Node parent) {
            this.parent = parent;
            this.key = key;
        }

        public Node(Node parent, Node left, Node right, Integer key) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.key = key;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node)node).right;
    }

    @Override
    public Object string(Object node) {
        Node myNode = (Node)node;

        return myNode.key + "";
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        bst.treeInsert(bst, new Node(12));
        bst.treeInsert(bst, new Node(5));
        bst.treeInsert(bst, new Node(2));
        bst.treeInsert(bst, new Node(9));
        bst.treeInsert(bst, new Node(18));
        bst.treeInsert(bst, new Node(15));
        bst.treeInsert(bst, new Node(19));
        bst.treeInsert(bst, new Node(17));

        bst.inorderTreeWalk(bst.root);

        System.out.println();

        BinaryTrees.print(bst);

//        //两种方式删除根节点12
        bst.treeDelete(bst, bst.treeSearch(bst.root, 12));
//        bst.remove(bst.treeSearch(bst.root, 12));

//        //两种方式删除叶子节点17
//        bst.treeDelete(bst, bst.treeSearch(bst.root, 17));
//        bst.remove(bst.treeSearch(bst.root, 17));

//        //两种方式删除只有一个孩子的节点15
//        bst.treeDelete(bst, bst.treeSearch(bst.root, 15));
//        bst.remove(bst.treeSearch(bst.root, 15));

        System.out.println();

        BinaryTrees.print(bst);
    }
}
