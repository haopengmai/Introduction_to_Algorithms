package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter13;

public class RedBlackTree implements BinaryTreeInfo {
    //红黑直接用布尔变量定义
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    //初始化一个唯一的叶结点
    private final RBNode nil = new RBNode();

    //根结点初始化为nil
    private RBNode root = nil;

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }

    public RedBlackTree() {
        this.root = nil;
    }

    public RedBlackTree(RBNode root) {
        this.root = root;
    }

    //前序遍历二叉树
    public void preorderTreeWalk(RBNode x) {
        if(x != nil) {
            System.out.print(x.key + " ");
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    //中序遍历二叉树
    public void inorderTreeWalk(RBNode x) {
        if(x != nil) {
            inorderTreeWalk(x.left);
            System.out.print(x.key + " ");
            inorderTreeWalk(x.right);
        }
    }

    //后序遍历二叉树
    public void postorderTreeWalk(RBNode x) {
        if(x != nil) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.print(x.key + " ");
        }
    }

    //在二叉搜索树中查询某个值，递归版本
    public RBNode treeSearch(RBNode x, Integer k) {
        if(x == nil || k == x.key) {
            return x;
        }

        if(k < x.key) {
            return treeSearch(x.left, k);
        } else {
            return treeSearch(x.right, k);
        }
    }

    //在二叉搜索树中查询某个值，循环版本
    public RBNode iterativeTreeSearch(RBNode x, Integer k) {
        while(x != nil && k != x.key) {
            if(k < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        return x;
    }

    //在二叉搜索树中查找包含最小值的节点
    public RBNode treeMinimum(RBNode x) {
        while (x.left != nil) {
            x = x.left;
        }

        return x;
    }

    //在二叉搜索树中查找包含最大值的节点
    public RBNode treeMaximum(RBNode x) {
        while (x.right != nil) {
            x = x.right;
        }

        return x;
    }

    //查找节点x的后继节点
    public RBNode treeSuccessor(RBNode x) {
        if(x.right != nil) {  //x的右子树不为空，找右子树的最小值
            return treeMinimum(x.right);
        }

        RBNode y = x.parent;
        while(y != nil && x == y.right) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    //查找节点x的前驱节点
    public RBNode treePredeceessor(RBNode x) {
        if(x.left != nil) {
            return treeMaximum(x.left);
        }

        RBNode y = x.parent;
        while(y != nil && x == y.left) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /**
     * 围绕x左旋
     *       xp                    xp
     *      /                     /
     *     x                     xr
     *    / \          ==>      / \
     *  xl  xr                 x   rr
     *     / \                / \
     *    rl  rr             xl  rl
     *
     * @param t,x
     */
    void leftRotate(RedBlackTree t, RBNode x) {
        RBNode y = x.right;   //让y等于x的右子节点
        x.right = y.left;    //将y的左子树转成x的右子树
        if(y.left != t.nil) {  //假如y的左孩子不为空，将y的左孩子的父亲设置为x
            y.left.parent = x;
        }
        y.parent = x.parent;  //将y的父亲设置为x的父亲
        if(x.parent == t.nil) {
            t.root = y;  //考虑x原来为根节点的情况
        } else if (x.parent.left == x) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    /**
     * 围绕x右旋
     *    xp                xp
     *     \                 \
     *      x                xl
     *     / \      =>      /  \
     *   xl  xr            ll   x
     *   / \                   / \
     *  ll lr                 lr  xr
     *
     * @param t,x
     */
    void rightRotate(RedBlackTree t, RBNode x) {
        RBNode y = x.left;   //让y等于x的左子节点
        x.left = y.right;    //将y的右子树转成x的左子树
        if(y.right != t.nil) {  //假如y的右孩子不为空，将y的右孩子的父亲设置为x
            y.right.parent = x;
        }
        y.parent = x.parent;  //将y的父亲设置为x的父亲
        if(x.parent == t.nil) {
            t.root = y;  //考虑x原来为根节点的情况
        } else if (x.parent.left == x) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.right = x;
        x.parent = y;
    }


    //红黑树的插入操作
    public void RBInsert(RedBlackTree t, RBNode z) {
        RBNode y = t.nil;
        RBNode x = t.root;
        while(x != t.nil) {
            y = x;
            if(z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;
        if(y == t.nil) {  //说明现在是棵空树
            t.root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }

        z.left = t.nil;
        z.right = t.nil;
        z.color = RED;
        rbInsertFixup(t, z);
    }

    //插入节点后维护红黑树性质的过程
    public void rbInsertFixup(RedBlackTree t, RBNode z) {
        while (z.parent.color == RED) {
            if(z.parent == z.parent.parent.left) {  //z的父节点是z的祖父节点的左孩子
                RBNode y = z.parent.parent.right;   //让y成为z的叔叔节点
                if (y.color == RED) {
                    /*
                     * z的父亲和叔叔节点都是红色，
                     * 根据红黑树性质，z的祖父一定是黑色
                     * 所以这时候，可以把z的祖父设为红色，
                     * z的父亲和叔叔都设为黑色，但这可能
                     * 导致z的祖父产生双红节点的问题，这
                     * 时候把z设置成z的祖父，继续向上递归
                     */
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        /*
                         * z的父亲是左节点，z是右节点,
                         * 满足LR不平衡，需要对z的父亲进行左旋,
                         * 转变成LL不平衡
                         */
                        z = z.parent;
                        leftRotate(t, z);
                    }
                    /*
                     * z的父亲是左节点，z也是左节点，
                     * 满足LL不平衡，把z的父亲设为黑色，
                     * z的祖父设为红色，对z进行右旋，
                     * 即可满足平衡
                     */
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(t, z.parent.parent);
                }
            } else {
                RBNode y = z.parent.parent.left;   //让y成为z的叔叔节点
                if (y.color == RED) {
                    /*
                     * z的父亲和叔叔节点都是红色，
                     * 根据红黑树性质，z的祖父一定是黑色
                     * 所以这时候，可以把z的祖父设为红色，
                     * z的父亲和叔叔都设为黑色，但这可能
                     * 导致z的祖父产生双红节点的问题，这
                     * 时候把z设置成z的祖父，继续向上递归
                     */
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        /*
                         * z的父亲是右节点，z是左节点,
                         * 满足RL不平衡，需要对z的父亲进行右旋,
                         * 转变成RR不平衡
                         */
                        z = z.parent;
                        rightRotate(t, z);
                    }
                    /*
                     * z的父亲是右节点，z也是右节点，
                     * 满足RR不平衡，把z的父亲设为黑色，
                     * z的祖父设为红色，对z进行左旋，
                     * 即可满足平衡
                     */
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(t, z.parent.parent);
                }
            }
        }

        t.root.color = BLACK;
    }

    public void rbTransplant(RedBlackTree t, RBNode u, RBNode v) {
        if(u.parent == t.nil) {
            t.root = v;
        } else if(u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        v.parent = u.parent;
    }

    //删除节点操作
    public void rbDelete(RedBlackTree t, RBNode z) {
        RBNode x;
        RBNode y = z;
        boolean yOriginalColor = y.color;
        if (z.left == t.nil) {
            x = z.right;
            //z的左孩子为空，用z的右孩子替换z，此时z的右孩子可以为空，也可以不为空
            rbTransplant(t, z, z.right);
        } else if (z.right == t.nil) {
            x = z.left;
            //z仅有一个孩子且其为左孩子，用z的左孩子替换z
            rbTransplant(t, z , z.left);
        } else {
            //z有两个孩子，找z的后继节点
            y = treeMinimum(z.right);

            yOriginalColor = y.color;
            x = y.right;
            if(y.parent == z) {
                x.parent = y;
            } else {
                //用以y的右孩子为根的树代替以y为根的树
                rbTransplant(t, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            //用以y为根的树代替以z为根的树
            rbTransplant(t, z, y);
            y.left = z.left;
            y.left.parent = y;
        }

        //如果删除的为黑节点，需要修复平衡
        if (yOriginalColor == BLACK) {
//            rbDeleteFixup(t, x);
            fixAfterDelete(t, x);
        }
    }

    //删除修复算法导论版代码，用红黑树解释
    public void rbDeleteFixup(RedBlackTree t, RBNode x) {
        while(x != t.root && x.color == BLACK) {
            //x是左孩子
            if(x == x.parent.left) {
                //w为兄弟节点
                RBNode w = x.parent.right;
                //w为红色，要旋转成黑色，方便后续操作
                if(w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(t, x.parent);
                    //此时w为黑色
                    w = x.parent.right;
                }

                /*
                 * w是黑色，w的两个孩子都是黑色，
                 * 没办法通过旋转恢复平衡，只能把w拉下水，设为红色，
                 * x变成x的父节点，向上递归
                 */
                if(w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    /*
                     * w是黑色，w的右孩子是黑色，
                     * w的左孩子是红色，为RL场景
                     * 此时把w的左孩子设为黑色，
                     * w设为红色，对w进行右旋，
                     * 确保w的右孩子为红色，此时为RR场景
                     */
                    if(w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(t, w);
                        w = x.parent.right;
                    }

                    /*
                     * 此时一定是w为黑色，w的右孩子为红色，
                     * w设置为x父节点的颜色，
                     * x父节点设为黑色，
                     * w的右孩子设置为黑色，
                     * 这样w路径上多出来一个黑节点，
                     * 对x的父节点进行左旋，
                     * 相当于把原来w路径上多出来的黑节点补充到x的路径上，
                     * 这样就填补了原来删除的黑节点，恢复平衡
                     */
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(t, x.parent);
                    /*
                     * 这里已经恢复平衡，
                     * 所以直接把x设置为根节点结束循环
                     */
                    x = t.root;
                }
            } else {
                //x是右孩子，w是兄弟节点
                RBNode w = x.parent.left;
                //w为红色，要旋转成黑色，方便后续操作
                if(w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(t, x.parent);
                    //此时w为黑色
                    w = x.parent.right;
                }

                /*
                 * w是黑色，w的两个孩子都是黑色，
                 * 没办法通过旋转恢复平衡，只能把w拉下水，设为红色，
                 * x变成x的父节点，向上递归
                 */
                if(w.right.color == BLACK && w.left.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    /*
                     * w是黑色，w的左孩子是黑色，
                     * w的右孩子是红色，为LR场景
                     * 此时把w的右孩子设为黑色，
                     * w设为红色，对w进行左旋，
                     * 确保w的左孩子一定为红色，此时为LL场景
                     */
                    if(w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(t, w);
                        w = x.parent.left;
                    }

                    /*
                     * 此时一定是w为黑色，w的左孩子为红色，
                     * w设置为x父节点的颜色，
                     * x父节点设为黑色，
                     * w的左孩子设置为黑色，
                     * 这样w路径上多出来一个黑节点，
                     * 对x的父节点进行右旋，
                     * 相当于把原来w路径上多出来的黑节点补充到x的路径上，
                     * 这样就填补了原来删除的黑节点，恢复平衡
                     */
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(t, x.parent);
                    /*
                     * 这里已经恢复平衡，
                     * 所以直接把x设置为根节点结束循环
                     */
                    x = t.root;
                }
            }
        }
        /*
         * 循环结束，要么x是根节点，
         * 要么x是红色节点，
         * 直接把x设置成黑色，即可恢复平衡
         */
        x.color = BLACK;
    }

    /**
     * 根据2-3-4树解释的红黑树删除
     * 删除后的调整处理
     * 1.情况1：自己能搞定，对应的叶子节点是3节点或者4节点
     * 2.情况2：自己搞不定，需要兄弟节点借，但是兄弟节点不能直接借，找父亲节点借，父亲下来，然后兄弟节点找一个人代替父亲当家
     * 3.情况3：跟兄弟节点借，兄弟也没有
     * @param t,x
     */
    public void fixAfterDelete(RedBlackTree t, RBNode x){
        while(x != t.root && x.color == BLACK){
            //x是左孩子的情况
            if(x == x.parent.left) {
                //兄弟节点
                RBNode w = x.parent.right;

                //判断此时兄弟节点是否是真正的兄弟节点，只有黑色节点才是
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(t, x.parent);
                    //找到真正的兄弟节点
                    w = x.parent.right;
                }
                //情况三，找兄弟借，兄弟没得借
                if(w.left.color == BLACK && w.right.color == BLACK) {
                    //把兄弟拉下水，设为红色，向上递归
                    w.color = RED;
                    x = x.parent;
                }
                //情况二，找兄弟借，兄弟有的借
                else{
                    //确保w的右孩子是红色
                    if(w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(t, w);
                        w = x.parent.right;
                    }

                    /*
                     * 此时一定是w为黑色，w的右孩子为红色，
                     * w设置为x父节点的颜色，
                     * x父节点设为黑色，
                     * w的右孩子设置为黑色，
                     * 这样w路径上多出来一个黑节点，
                     * 对x的父节点进行左旋，
                     * 相当于把原来w路径上多出来的黑节点补充到x的路径上，
                     * 这样就填补了原来删除的黑节点，恢复平衡
                     */
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(t, x.parent);

                    x = t.root;
                }
            }
            //x是右孩子的情况
            else{
                //兄弟节点
                RBNode w = x.parent.left;
                //判断此时兄弟节点是否是真正的兄弟节点，只有黑色节点才是
                if(w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(t, x.parent);
                    //此时w为黑色，才是真正的兄弟节点
                    w = x.parent.right;
                }
                //情况三，找兄弟借，兄弟没得借
                if(w.left.color == BLACK && w.right.color == BLACK) {
                    //把兄弟拉下水，设为红色，向上递归
                    w.color = RED;
                    x = x.parent;
                }
                //情况二，找兄弟借，兄弟有的借
                else{
                    //确保w的左孩子是红色
                    if(w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(t, w);
                        w = x.parent.right;
                    }

                    /*
                     * 此时一定是w为黑色，w的左孩子为红色，
                     * w设置为x父节点的颜色，
                     * x父节点设为黑色，
                     * w的左孩子设置为黑色，
                     * 这样w路径上多出来一个黑节点，
                     * 对x的父节点进行右旋，
                     * 相当于把原来w路径上多出来的黑节点补充到x的路径上，
                     * 这样就填补了原来删除的黑节点，恢复平衡
                     */
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(t, x.parent);

                    x = t.root;
                }
            }
        }
        //情况一、替代节点是红色，则直接染红，补偿删除的黑色节点，这样红黑树依然保持平衡
        x.color = BLACK;
    }

    //红黑树节点类定义
    static class RBNode {
        private Integer key;  //节点值
        private RBNode parent;  //父节点
        private RBNode left;   //左孩子
        private RBNode right;   //右孩子
        private boolean color = BLACK;

        public RBNode() {

        }

        public RBNode(Integer key) {
            this.key = key;
        }

        public RBNode(Integer key, RBNode parent) {
            this.parent = parent;
            this.color = BLACK;
            this.key = key;
        }

        public RBNode(RBNode parent, RBNode left, RBNode right, Integer key, boolean color) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.key = key;
            this.color = color;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object RBNode) {
        return ((RBNode)RBNode).left;
    }

    @Override
    public Object right(Object RBNode) {
        return ((RBNode)RBNode).right;
    }

    @Override
    public Object string(Object RBNode) {
        RBNode myRBNode = (RBNode)RBNode;

        String color = myRBNode.color == RED ? "RED" : "BLACK";

        return myRBNode.key + "(" + color + ")";
    }

    public static void main(String[] args) {
        RedBlackTree bst = new RedBlackTree();

        bst.RBInsert(bst, new RBNode(12));
        bst.RBInsert(bst, new RBNode(5));
        bst.RBInsert(bst, new RBNode(2));
        bst.RBInsert(bst, new RBNode(9));
        bst.RBInsert(bst, new RBNode(18));
        bst.RBInsert(bst, new RBNode(15));
        bst.RBInsert(bst, new RBNode(19));
        bst.RBInsert(bst, new RBNode(17));

        bst.inorderTreeWalk(bst.root);

        System.out.println();

        BinaryTrees.print(bst);

        bst.rbDelete(bst, bst.treeSearch(bst.root, 12));

        System.out.println();

        BinaryTrees.print(bst);

//        //两种方式删除根节点12
//        bst.treeDelete(bst, bst.treeSearch(bst.root, 12));
//        bst.remove(bst.treeSearch(bst.root, 12));

//        //两种方式删除叶子节点17
//        bst.treeDelete(bst, bst.treeSearch(bst.root, 17));
//        bst.remove(bst.treeSearch(bst.root, 17));

//        //两种方式删除只有一个孩子的节点15
//        bst.treeDelete(bst, bst.treeSearch(bst.root, 15));
//        bst.remove(bst.treeSearch(bst.root, 15));
//
//        System.out.println();
//
//        BinaryTrees.print(bst);
    }
}
