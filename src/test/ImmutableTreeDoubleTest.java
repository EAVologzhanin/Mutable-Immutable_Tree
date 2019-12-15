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

class ImmutableTreeDoubleTest {
    private ImmutableTree<Double> tree;
    private ImmutableNode<Double> root;
    private ImmutableNode<Double> testChild;

    @BeforeEach
    void setUp() {
        root = new ImmutableNode<Double>((double) -5, null, immutableNode -> {
            ArrayList<ImmutableNode<Double>> subTree = new ArrayList<>();
            ImmutableNode<Double> otherNode = new ImmutableNode<Double>((double) 7, immutableNode, immutableNode2 -> {
                ArrayList<ImmutableNode<Double>> secondSubtree = new ArrayList<ImmutableNode<Double>>();
                secondSubtree.add(new ImmutableNode<Double>((double) -3, immutableNode2, (Collection<Node<Double>>) null));
                secondSubtree.add(new ImmutableNode<Double>((double) -4, immutableNode2, (Collection<Node<Double>>) null));
                ImmutableNode<Double> someNode = new ImmutableNode<Double>((double) 15, immutableNode2, (Collection<Node<Double>>) null);
                secondSubtree.add(someNode);
                secondSubtree.add(new ImmutableNode<Double>((double) -4, immutableNode2, (Collection<Node<Double>>) null));
                return secondSubtree;
            });
            testChild = otherNode;

            subTree.add(otherNode);
            subTree.add(new ImmutableNode<Double>((double) 2, immutableNode, (Collection<Node<Double>>) null));
            subTree.add(new ImmutableNode<Double>((double) -3, immutableNode, (Collection<Node<Double>>) null));
            subTree.add(new ImmutableNode<Double>((double) 6, immutableNode, (Collection<Node<Double>>) null));

            ImmutableNode<Double> otherNode2 = new ImmutableNode<Double>((double)-5, immutableNode, integerImmutableNode2 -> {
                ArrayList<ImmutableNode<Double>> secondSubtree = new ArrayList<ImmutableNode<Double>>();
                secondSubtree.add(new ImmutableNode<Double>((double) -3, integerImmutableNode2, (Collection<Node<Double>>) null));
                secondSubtree.add(new ImmutableNode<Double>((double) 4, integerImmutableNode2, (Collection<Node<Double>>) null));
                secondSubtree.add(new ImmutableNode<Double>((double) -4, integerImmutableNode2, (Collection<Node<Double>>) null));
                secondSubtree.add(new ImmutableNode<Double>((double) 8, integerImmutableNode2, (Collection<Node<Double>>) null));
                return secondSubtree;
            });
            subTree.add(otherNode2);
            return subTree;
        });
        tree = new ImmutableTree<Double>(Double::sum,Double::compareTo, root, (double) 0);
        tree.print();
    }

    @Test
    void findNegativeNodes() {
        ArrayList<Node<Double>> list = ((ArrayList<Node<Double>>)tree.findNegativeNodes(root));
        list.sort(Comparator.comparing(x -> tree.getSum(x)));
        for (Node<Double> doubleNode : list) {
            Assert.assertEquals(-1, tree.getSum(doubleNode).compareTo(0.0));
        }
        Assert.assertEquals(6,list.size());
    }

    @Test
    void getRoot() {
        Assert.assertEquals(root,tree.getRoot());
    }

    @Test
    void getSum() {
        Assert.assertEquals((Double)11.0, tree.getSum());
    }

    @Test
    void testGetSum() {
        Assert.assertEquals((Double)11.0, tree.getSum(root));
        Assert.assertEquals((Double)11.0, tree.getSum(testChild));
    }

    @Test
    void getSize() {
        Assert.assertEquals(14 ,tree.getSize());
    }

    @Test
    void testGetSize() {
        Assert.assertEquals(14 ,tree.getSize(root));
        Assert.assertEquals(5 ,tree.getSize(testChild));
    }

    @Test
    void removeSubtree() {
        Iterator iterator = root.getChildren().iterator();
        Assert.assertEquals((Double) 0.0, tree.removeSubtree(root).getSum());
        Assert.assertEquals((Double) 0.0, tree.removeSubtree(testChild).getSum());
        Assert.assertEquals(7.0,((ImmutableNode)(iterator.next())).getValue());
        Assert.assertEquals(2.0,((ImmutableNode)(iterator.next())).getValue());
    }

    @Test
    void maximize() {
        Assert.assertEquals((Double)32.0,tree.maximize().getSum());
    }
}