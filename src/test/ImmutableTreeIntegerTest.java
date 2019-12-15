package test;

import eavologzhanin.ImmutableNode;
import eavologzhanin.ImmutableTree;
import eavologzhanin.Node;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

class ImmutableTreeIntegerTest {
    private ImmutableTree<Integer> tree;
    private ImmutableNode<Integer> root;
    private ImmutableNode<Integer> testChild;
    @BeforeEach
    void setUp() {
        root = new ImmutableNode<Integer>(1, null, immutableNode -> {
            ArrayList<ImmutableNode<Integer>> subTree = new ArrayList<>();
            subTree.add(new ImmutableNode<Integer>(2, immutableNode, (Collection<Node<Integer>>) null));
            subTree.add(new ImmutableNode<Integer>(-3, immutableNode, (Collection<Node<Integer>>) null));

            ImmutableNode<Integer> otherNode = new ImmutableNode<Integer>(2, immutableNode, immutableNode2 -> {
                ArrayList<ImmutableNode<Integer>> secondSubtree = new ArrayList<ImmutableNode<Integer>>();
                secondSubtree.add(new ImmutableNode<Integer>(-3, immutableNode2, (Collection<Node<Integer>>) null));
                secondSubtree.add(new ImmutableNode<Integer>(4, immutableNode2, (Collection<Node<Integer>>) null));
                ImmutableNode<Integer> someNode = new ImmutableNode<Integer>(-5, immutableNode2, (Collection<Node<Integer>>) null);
                testChild = someNode;
                secondSubtree.add(someNode);
                return secondSubtree;
            });
            subTree.add(otherNode);
            return subTree;
        });
        tree = new ImmutableTree<Integer>(Integer::sum,Integer::compareTo, root,0);
        tree.print();
    }

        @AfterEach
    void tearDown() {
    }

    @Test
    void findNegativeNodes() {
        ArrayList<Node<Integer>> list = ((ArrayList<Node<Integer>>)tree.findNegativeNodes(root));
        list.sort(Comparator.comparing(x -> tree.getSum(x)));
        for (Node<Integer> integerNode : list) {
            Assert.assertEquals(-1, tree.getSum(integerNode).compareTo(0));
        }
        Assert.assertEquals(5,list.size());
    }

    @Test
    void getRoot(){
        Assert.assertEquals(root,tree.getRoot());
    }

    @Test
    void getSum() {
        Assert.assertEquals((Integer)(-2),tree.getSum());
    }

    @Test
    void testGetSum() {
        Assert.assertEquals((Integer)(-2),tree.getSum(root));
        Assert.assertEquals((Integer)(-5),tree.getSum(testChild));
    }

    @Test
    void getSize() {
        Assert.assertEquals(7,tree.getSize());
    }

    @Test
    void testGetSize() {
        Assert.assertEquals(7,tree.getSize());
    }

    @Test
    void removeSubtree() {
        Iterator iterator = root.getChildren().iterator();
        Assert.assertEquals((Integer) 0, tree.removeSubtree(root).getSum());
        Assert.assertEquals((Integer) 3, tree.removeSubtree(testChild).getSum());
        Assert.assertEquals(2,((ImmutableNode)(iterator.next())).getValue());
        Assert.assertEquals(-3,((ImmutableNode)(iterator.next())).getValue());
    }

    @Test
    void maximize() {
        Assert.assertEquals((Integer)9,tree.maximize().getSum());
    }
}