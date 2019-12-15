package test;

import eavologzhanin.MutableNode;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableNodeTest {

    MutableNode<Integer> node;
    @BeforeEach
    void setUp() {
        node = new MutableNode<>(10);
    }
    @Test
    void setValue() {
        node.setValue(20);
        assertEquals(20,node.getValue());
    }

    @Test
    void setParent() {
        MutableNode<Integer> parent = new MutableNode<>(3);
        node.setParent(parent);
        Assert.assertEquals(parent.getValue(), node.getParent().getValue());
    }

    @Test
    void setChildren() {
        node.setChildren(null);
        Assert.assertEquals(0,node.getChildren().size());
    }

    @Test
    void addChild() {
        node.addChild(new MutableNode<>(5));
        Assert.assertEquals((Integer)5,node.getChild(0).getValue());
    }

    @Test
    void getParent() {
        Assert.assertNull(node.getParent());
    }

    @Test
    void getChild() {
        node.addChild(new MutableNode<>(30));
        Assert.assertEquals((Integer)30,node.getChild(0).getValue());
        node.addChild(new MutableNode<>(40));
        Assert.assertEquals((Integer)40,node.getChild(1).getValue());
    }

    @Test
    void getChildren() {
        node.addChild(new MutableNode<>(30));
        Assert.assertEquals((Integer)30,node.getChild(0).getValue());
        node.addChild(new MutableNode<>(40));
        Assert.assertEquals((Integer)40,node.getChild(1).getValue());
        Assert.assertEquals(2,node.getChildren().size());
    }

    @Test
    void getValue() {
        Assert.assertEquals((Integer)10,node.getValue());
    }
}