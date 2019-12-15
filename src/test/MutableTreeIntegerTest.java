package test;

import eavologzhanin.MutableNode;
import eavologzhanin.MutableTree;
import eavologzhanin.Node;
import eavologzhanin.Wrapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MutableTreeIntegerTest  {
    private MutableNode<Integer> root;
    private MutableTree<Integer> mutableTree;
    private ArrayList<MutableNode<Integer>> listIntegerMutableNode;
    private Integer sum = 0;
    private final Integer countOfNodes = 18;

    @BeforeEach
    void setUp() {
        root = new MutableNode<Integer>(0);
        listIntegerMutableNode = new ArrayList<MutableNode<Integer>>(countOfNodes);
        ArrayList<Integer> listIntegers = new ArrayList<Integer>(countOfNodes);

        for (int i = 0; i < countOfNodes; i++) {
            listIntegers.add(i,(int)Math.pow(-1,i)*10*i);
            sum+= listIntegers.get(i);
        }

        Iterator<Integer> iterator = listIntegers.iterator();
        for (int i = 0; i < 3; i++) {
            listIntegerMutableNode.add(new MutableNode<Integer>(iterator.next(),root));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                listIntegerMutableNode.add(new MutableNode<Integer>(iterator.next(),listIntegerMutableNode.get(i)));
            }
        }
        mutableTree = new MutableTree<>(Integer::sum,Integer::compareTo,root,0);
    }

    @AfterEach
    void tearDown() {
        sum = 0;
        mutableTree.print();
    }

    @Test
    void getSize() {
        Assert.assertEquals(countOfNodes+1,mutableTree.getSize()); // Прибавляем корень дерева
    }
    @Test
    void testGetSize() {
        Assert.assertEquals(6,mutableTree.getSize(listIntegerMutableNode.get(2))); // Прибавляем корень дерева
    }

    @Test
    void getSum() {
        Assert.assertEquals(sum,mutableTree.getSum());
    }

    @Test
    void testGetSum() {
        Assert.assertEquals((Integer)(-130),mutableTree.getSum(listIntegerMutableNode.get(2)));
        Assert.assertEquals((Integer)(90),mutableTree.getSum(listIntegerMutableNode.get(1)));
        Assert.assertEquals((Integer)(-50),mutableTree.getSum(listIntegerMutableNode.get(0)));
    }

    @Test
    void removeSubtree() {
        Assert.assertEquals((Integer)(-180), mutableTree.removeSubtree(listIntegerMutableNode.get(1)).getSum());
        Assert.assertEquals((Integer)(-130), mutableTree.removeSubtree(listIntegerMutableNode.get(0)).getSum());
        Assert.assertEquals((Integer)(0), mutableTree.removeSubtree(listIntegerMutableNode.get(2)).getSum());
    }

    @Test
    void maximize() {
        Assert.assertEquals((Integer)710, mutableTree.maximize().getSum());
    }

    /*
    * Находим в списке узлом из которых состоит наше дерево те, в которых сумма поддеревьев < 0.
    * После чего сортируем его по сумме поддерева, аналогично сортируем то, что вернет нам метод в дереве.
    * Пройдемся итератором, и сравним значения
    */
    @Test
    void findNegativeNodes(){
        mutableTree.print();
        ArrayList<Node<Integer>> list = ((ArrayList<Node<Integer>>)mutableTree.findNegativeNodes(root));
        listIntegerMutableNode.sort(Comparator.comparing(MutableNode::getValue));
        Iterator<MutableNode<Integer>> iterator = listIntegerMutableNode.iterator();
        Integer[] x = {-170,-150,-130,-110,-90,-70,-50,-30,-10,0};
        for (int i = 0; i < x.length; i++) {
            Assert.assertEquals( x[i], iterator.next().getValue());
        }
    }
    @Test
    void getRoot() {
        Assert.assertEquals(root,mutableTree.getRoot());
    }

//    @Test
//    void countOfChildren() {
//        Assert.assertEquals(15,mutableTree.countOfChildren(root));
//    }

    @Test
    void testMaximize() {

    }
}