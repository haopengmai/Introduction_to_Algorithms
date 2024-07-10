package com.tnwb.com.IntroductiontoAlg.com.chapter8.IntroductiontoAlg.com.chapter16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 连通无向图最小生成树Prim算法Java实现
 */
public class Prim {
    public static List<Node> prim(Graph graph){
        //1、初始化一个最小优先队列
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        //这里第一个顶点的key是0，其他都是Integer.MAX_VALUE 代表无穷
        List<Node> nodes = graph.getVertexs();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            node.setParent(null);
            if(i==0) {
                node.setKey(0);
            }else {
                node.setKey(Integer.MAX_VALUE);
            }
            priorityQueue.add(node);
        }
        //2、初始化生成树A，一开始树A为空，全部节点都还在Q中
        List<Node> A = new ArrayList<>();
        /**
         * 上面初始化最小堆的时间复杂度为O(V)
         */
        //3、开始执行逻辑
        while(priorityQueue.getSize()!=0) {//这个要执行|V|次
            //4、获取第一个节点
            Node minNode = priorityQueue.extractMin();//这一步的时间复杂度是：O(VlgV)
            //5、取出这个节点对应的连接边
            List<Node> adjNodes = graph.getAdjacentVertexIndex(minNode);
            for (int i = 0; i < adjNodes.size(); i++) {//这里要执行|E|次
                //6、检查该节点是否属于集合Q
                Node node = adjNodes.get(i);
                /**
                 * 对每个节点维护一个标志位来指明该节点是否属于Q，
                 * 并在将节点Q从Q中移除的时候将标志位进行更新,时间复杂度则为O(1)
                 */
                if(node.getInQ()==1) {
                    //7、获取这个邻接边的权重
                    int weight = graph.edges[minNode.getIndex()][node.getIndex()];
                    if(weight<node.getKey()) {
                        //8、更新node的值
                        node.setParent(minNode);
                        //这一行操作涉及后面需要重新建堆
                        node.setKey(weight);
                        //重新建堆
                        priorityQueue.rebuildHeap(node);//这一步的时间复杂度是O(ElgV)
                    }
                }
            }
            //10，将该节点加入集合A
            minNode.setInQ(0);
            A.add(minNode);
        }

        //所以时间复杂度为O(V+VlgV+ElgV)=O(ElgV)=O(ElgV)
        return A;
    }

    /**
     * 打印最小生成树的节点A
     * @param A
     */
    public static void printPrim(List<Node> A) {
        //这里就已经生成了最小生成树了，遍历输出A
        for (int i = 0; i < A.size(); i++) {
            Node node = A.get(i);
            String vertex =  node.getValue();
            Node parent = node.getParent();
            String pVertex = "NIL";
            if(parent!=null) {
                pVertex=parent.getValue();
            }
            System.out.println("节点："+vertex+";父节点："+pVertex+";权重："+node.getKey());
        }
    }

    public static Graph getGraph() {
        Graph graph = new Graph(9);
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");
        Node h = new Node("h");
        Node i = new Node("i");
        graph.addVertex(b);
        graph.addVertex(a);
 //       graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addEdge(a, b,4);
        graph.addEdge(a, h,8);
        graph.addEdge(b, c,8);
        graph.addEdge(b, h,11);
        graph.addEdge(c, d,7);
        graph.addEdge(c, i,2);
        graph.addEdge(c, f,4);
        graph.addEdge(i, h,7);
        graph.addEdge(i, g,6);
        graph.addEdge(h, g,1);
        graph.addEdge(g, f,2);
        graph.addEdge(d, f,14);
        graph.addEdge(d, e,9);
        graph.addEdge(f, e,10);
        return graph;
    }

    public static void main(String[] args) {
        //获取一个图
        Graph graph = getGraph();
        graph.printGraph();
        //获取该图的最小生成树
        List<Node> A = prim(graph);
        printPrim(A);
    }
}

//图的节点，当然也是最小优先队列Q的节点，也是最小生成树A的节点：因此多了属性key，parent，inQ
class Node implements Comparable<Node>{
    private int index;//坐标
    private int  key;//每个节点的key属性
    private String value;//节点名称
    private Node parent;//父节点
    private int inQ=1;//是否属于Q，默认是属于的，等离开才不属于

    @Override
    public int compareTo(Node o) {
        return this.key-o.key;
    }
    public Node(String value) {
        super();
        this.value = value;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public int getInQ() {
        return inQ;
    }
    public void setInQ(int inQ) {
        this.inQ = inQ;
    }
}


//定义图：无向图
class Graph{
    //顶点数目
    int size;

    int index=0;

    //顶点
    private List<Node> vertexs;

    //边
    int[][] edges;

    //初始化
    public Graph(int size) {
        this.size=size;
        this.vertexs=new ArrayList<Node>(size);
        this.edges=new int[size][size];
    }

    //添加顶点
    public void addVertex(Node node) {
        node.setIndex(index);
        vertexs.add(node);
        index++;
    }

    //添加边:有权重有方向
    public void addEdge(Node vertex1,Node vertex2,int weight) {
        //因为是无向图，所以这里直接把两条边都加上
        edges[vertex1.getIndex()][vertex2.getIndex()]=weight;
        edges[vertex2.getIndex()][vertex1.getIndex()]=weight;
    }

    public List<Node> getVertexs() {
        return vertexs;
    }

    /***************上面已经做好了图，现在做遍历********************/
    /**
     * 打印图
     */
    public void printGraph() {
        System.out.print("图\t ");
        for (Node vertex: vertexs) {
            System.out.print(vertex.getValue()+"\t");
        }
        System.out.println("\n");
        for (int i = 0; i <size; i++) {
            Node vertex = vertexs.get(i);
            System.out.print(vertex.getValue()+"\t");
            for (int j = 0; j < size; j++) {
                System.out.print(edges[i][j]+"\t");
            }
            System.out.println("\n");
        }
        System.out.println();
    }

    /**
     * 获取节点node相邻的节点
     * @param node
     * @return
     */
    public List<Node> getAdjacentVertexIndex(Node node) {
        List<Node> lists = new ArrayList<Node>();
        for (int j = 0; j < size; j++) {
            if(edges[node.getIndex()][j]!=0) {
                lists.add(vertexs.get(j));
            }
        }
        return lists;
    }
}

/**
 *最小堆实现的一个优先队列
 * @author suibibk@qq.com
 */
class PriorityQueue<E extends Comparable<E>> {
    //这里使用数组来实现
    private ArrayList<E> data;

    public PriorityQueue(int capacity) {
        data = new ArrayList<>(capacity);
    }

    public PriorityQueue() {
        data  = new ArrayList<>();
    }

    //返回堆中元素的个数
    public int getSize() {
        return data.size();
    }

    //返回二叉树中索引为index的节点的父节点索引
    public int parent(int index) {
        if(index == 0) {
            throw new IllegalArgumentException("index-0 dosen't have parent");
        }
        return (index-1)/2;
    }

    //返回完全二叉树中索引为index的节点的左孩子的索引
    private int leftChild(int index) {
        return index*2+1;
    }

    //返回完全二叉树中索引为index的节点的右孩子的索引
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    //交换索引为i、j的值
    private void swap(int i, int j) {
        E t = data.get(i);
        data.set(i, data.get(j));
        data.set(j, t);
    }

    //向堆中添加元素
    public void add(E e) {
        data.add(e);
        siftUp(data.size() - 1);
    }

    //上浮
    private void siftUp(int k) {
        //k不能是根节点，并且k的值要比父节点的值小
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) > 0) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    //看堆中最小的元素
    public E findMin() {
        if (data.size() == 0)
            throw new IllegalArgumentException("Can not findMax when heap is empty");
        return data.get(0);
    }

    //取出堆中最小的元素
    public E extractMin() {
        E ret = findMin();
        swap(0, data.size() - 1);
        data.remove(data.size() - 1);
        siftDown(0);
        return ret;
    }

    /**
     * 当修改了堆中一些元素后,要执行先向下浮再向上浮的操作，这里需要重新建堆
     */
    public void rebuildHeap(E e) {
        // 这里优化为在建优先队列的时候，就指定节点在优先队列中的坐标，那么就不需要使用循环
        for (int j = 0; j < data.size(); j++) {
            if(e==data.get(j)) {
                siftDown(j);
                siftUp(j);
                break;
            }
        }
    }

    //下浮
    private void siftDown(int k) {
        //leftChild存在
        while (leftChild(k) < data.size()) {
            int j = leftChild(k);
            //rightChild存在,且值小于leftChild的值
            if (j + 1 < data.size() &&
                    data.get(j).compareTo(data.get(j + 1)) > 0)
                j = rightChild(k);
            //data[j]是leftChild和rightChild中最小的
            if (data.get(k).compareTo(data.get(j)) <0)
                break;
            swap(k, j);
            k = j;
        }
    }

    //取出堆中最大的元素,替换为元素e
    public E replace(E e){
        E ret = findMin();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

    //heapify操作:将数组转化为堆
    public PriorityQueue(E[] arrs) {
        data = new ArrayList<>(Arrays.asList(arrs));
        for (int i = parent(data.size() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    public void print() {
        for (E e : data) {
            System.out.print(" "+e);
        }
    }

    public List<E> getDate() {
        return data;
    }
}
