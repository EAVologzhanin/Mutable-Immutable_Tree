package test;

import eavologzhanin.MutableNode;
import eavologzhanin.MutableTree;
import eavologzhanin.Node;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


class MutableTreeDoubleTest {
    private MutableNode<Double> root;
    private MutableTree<Double> mutableTree;
    private ArrayList<MutableNode<Double>> listDoubleMutableNode;
    private Double sum = 0.0;
    private final int countOfNodes = 32;

    @BeforeEach
    void setUp() {
        root = new MutableNode<Double>(0.0);
        listDoubleMutableNode = new ArrayList<MutableNode<Double>>(countOfNodes);
        ArrayList<Double> listDouble = new ArrayList<Double>(countOfNodes);

        for (int i = 0; i < countOfNodes; i++) {
            listDouble.add(i, (double) i*Math.pow(-1, i)*i-100);
            sum+= listDouble.get(i);
        }

        Iterator<Double> iterator = listDouble.iterator();
        for (int i = 0; i < 2; i++) {
            listDoubleMutableNode.add(new MutableNode<Double>(iterator.next(),root));
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                listDoubleMutableNode.add(new MutableNode<Double>(iterator.next(),listDoubleMutableNode.get(i)));
            }
        }
        for (int i = 2; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                listDoubleMutableNode.add(new MutableNode<Double>(iterator.next(),listDoubleMutableNode.get(i)));
            }
        }

        mutableTree = new MutableTree<Double>(Double::sum,Double::compareTo,root,0.0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findNegativeNodes() {
        ArrayList<Node<Double>> list = ((ArrayList<Node<Double>>)mutableTree.findNegativeNodes(root));
        list.sort(Comparator.comparing(x -> mutableTree.getSum(x)));
        for (Node<Double> doubleNode : list) {
            Assert.assertEquals(-1, mutableTree.getSum(doubleNode).compareTo(0.0));
        }
        Assert.assertEquals(22,list.size());
    }

    @Test
    void getRoot() {
        Assert.assertEquals(root,mutableTree.getRoot());
    }

    @Test
    void getSum() {
        Assert.assertEquals(sum, mutableTree.getSum());
    }

    @Test
    void testGetSum() {
        Assert.assertEquals((Double) (-534.0),mutableTree.getSum(listDoubleMutableNode.get(2)));
        Assert.assertEquals((Double) (-1945.0),mutableTree.getSum(listDoubleMutableNode.get(1)));
        Assert.assertEquals((Double) (-1751.0),mutableTree.getSum(listDoubleMutableNode.get(0)));
    }

    @Test
    void getSize() {
        Assert.assertEquals(countOfNodes+1,mutableTree.getSize()); // Прибавляем корень дерева
    }

    @Test
    void testGetSize() {
        Assert.assertEquals(5,mutableTree.getSize(listDoubleMutableNode.get(2)));
    }

    @Test
    void removeSubtree() {
        Assert.assertEquals((Double) (-3085.0), mutableTree.removeSubtree(listDoubleMutableNode.get(5)).getSum());
        Assert.assertEquals((Double) (-1334.0), mutableTree.removeSubtree(listDoubleMutableNode.get(0)).getSum());
        Assert.assertEquals((Double) (0.0), mutableTree.removeSubtree(listDoubleMutableNode.get(1)).getSum());
    }

    @Test
    void maximize() {
        Assert.assertEquals((Double)3008.0, mutableTree.maximize().getSum());
    }
}