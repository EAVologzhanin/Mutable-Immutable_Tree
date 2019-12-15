package test;

import eavologzhanin.ImmutableNode;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableNodeTest {
    private ImmutableNode<Integer> node;

    @BeforeEach
    void setUp() {
        node = new ImmutableNode<>(1,null);
    }

    @Test
    void getParent() {
        Assert.assertNull(node.getParent());
    }

    @Test
    void getChildren() {
        Assert.assertEquals(0,node.getChildren().size());
    }

    @Test
    void getValue() {
        Assert.assertEquals((Integer) 1,node.getValue());
    }
}