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

class MutableTreeLongTest {

    private MutableNode<Long> root;
    private MutableTree<Long> mutableTree;
    private ArrayList<MutableNode<Long>> listLongMutableNode;
    private Long sum = 0L;
    private final int countOfNodes = 16;

    @BeforeEach
    void setUp() {
        root = new MutableNode<Long>(0L);
        listLongMutableNode = new ArrayList<MutableNode<Long>>(countOfNodes);
        ArrayList<Long> listLonges = new ArrayList<>(countOfNodes);

        for (int i = 0; i < countOfNodes; i++) {
            listLonges.add(i, (long)(Math.pow(-1,i%3)*10*i + Math.pow(-1,i) ));
            sum+= listLonges.get(i);
        }

        Iterator<Long> iterator = listLonges.iterator();
        for (int i = 0; i < 4; i++) {
            listLongMutableNode.add(new MutableNode<Long>(iterator.next(),root));
        }
        for (int i = 4; i < 16; i++) {
            listLongMutableNode.add(new MutableNode<Long>(iterator.next(),listLongMutableNode.get(i-4)));
        }
        mutableTree = new MutableTree<Long>(Long::sum,Long::compareTo,root,0L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findNegativeNodes() {
        ArrayList<Node<Long>> list = ((ArrayList<Node<Long>>)mutableTree.findNegativeNodes(root));
        listLongMutableNode.removeIf((x)->mutableTree.getSum(x)>=0);
        listLongMutableNode.sort(Comparator.comparing(x -> mutableTree.getSum(x)));
        list.sort(Comparator.comparing(x -> mutableTree.getSum(x)));
        Iterator<Node<Long>> iterator1 = list.iterator();
        Iterator<MutableNode<Long>> iterator2 = listLongMutableNode.iterator();
        while(iterator1.hasNext() && iterator2.hasNext()){
            Assert.assertEquals(mutableTree.getSum(iterator1.next()),mutableTree.getSum(iterator2.next()));
        }
    }

    @Test
    void getRoot() {
        Assert.assertEquals(root, mutableTree.getRoot());
    }

    @Test
    void getSum() {
        Assert.assertEquals(sum, mutableTree.getSum());
    }

    @Test
    void testGetSum() {
        Assert.assertEquals((Long)(202L),mutableTree.getSum(listLongMutableNode.get(8)));
        Assert.assertEquals((Long)(163L),mutableTree.getSum(listLongMutableNode.get(4)));
        Assert.assertEquals((Long)(216L),mutableTree.getSum(listLongMutableNode.get(3)));
        Assert.assertEquals((Long)(124L),mutableTree.getSum(listLongMutableNode.get(2)));
        Assert.assertEquals((Long)(-4L),mutableTree.getSum(listLongMutableNode.get(1)));
    }

    @Test
    void getSize() {
        Assert.assertEquals(countOfNodes+1, mutableTree.getSize());
    }

    @Test
    void testGetSize() {
        Assert.assertEquals(countOfNodes+1,mutableTree.getSize(root));
        Assert.assertEquals(2,mutableTree.getSize(listLongMutableNode.get(8)));
        Assert.assertEquals(3,mutableTree.getSize(listLongMutableNode.get(5)));
        Assert.assertEquals(4,mutableTree.getSize(listLongMutableNode.get(3)));
    }

    @Test
    void removeSubtree() {
        Assert.assertEquals((Long) (336L), mutableTree.removeSubtree(listLongMutableNode.get(0)).getSum());
        Assert.assertEquals((Long)(340L), mutableTree.removeSubtree(listLongMutableNode.get(1)).getSum());
        Assert.assertEquals((Long) (216L), mutableTree.removeSubtree(listLongMutableNode.get(2)).getSum());
    }

    @Test
    void maximize() {
        Assert.assertEquals((Long) 631L, mutableTree.maximize().getSum());
    }
}